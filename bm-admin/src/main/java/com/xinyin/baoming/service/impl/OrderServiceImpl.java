package com.xinyin.baoming.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.TmClassMapper;
import com.xinyin.baoming.mapper.TmStudentMapper;
import com.xinyin.baoming.mapper.TtClassStudentMapper;
import com.xinyin.baoming.mapper.TtOrderDetailMapper;
import com.xinyin.baoming.mapper.TtOrderMapper;
import com.xinyin.baoming.mapper.TtShoppingCartMapper;
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TmStudent;
import com.xinyin.baoming.model.vo.TtClassStudent;
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.model.vo.TtOrderDetail;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IOrderService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 订单表信息管理服务层
 * 
 * 创建时间： 2016-08-14
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class OrderServiceImpl extends BaseService implements IOrderService {

	@Autowired
	private TtOrderMapper ttOrderMapper;
	
	@Autowired
	private TtOrderDetailMapper ttOrderDetailMapper;
	
	@Autowired
	private TmClassMapper tmClassMapper;
	
	@Autowired
	private TtShoppingCartMapper ttShoppingCartMapper;
	
	@Autowired
	private TtClassStudentMapper ttClassStudentMapper;
	
	@Autowired
	private TmStudentMapper tmStudentMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TtOrder>  selectBySelective( TtOrder record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("studentId",record.getStudentId());
			params.put("totalMoney",record.getTotalMoney());
			params.put("status",record.getStatus());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			params.put("phonenumber",record.getPhonenumber());
			params.put("idcardNo",record.getIdcardNo());
			return (PageList<TtOrder>) getPageList(TtOrderMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("订单表信息查询",e);
		}
	}

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Override
	public TtOrder selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return ttOrderMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得订单表信息",e);
		}
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String deleteByPrimaryKey( String id ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				TmClass toUpdate = new TmClass();
				TtOrder record = new TtOrder();
				record.setId(key);
				// 查询订单信息
				TtOrder to = ttOrderMapper.selectByPrimaryKey(record.getId());
				// 未完成支付的订单删除需要回滚相应班级剩余名额的数量，即剩余名额+1
				if(Constant.ORDER_STATUS_CANCEL.equals(to.getStatus()) || Constant.ORDER_STATUS_COMMITED.equals(to.getStatus())){
					// 查询关联订单明细
					TtOrderDetail param = new TtOrderDetail();
					param.setId(record.getId());
					List<TtOrderDetail> detail = ttOrderDetailMapper.selectBySelective(param);
					// 依次回滚相应班级的
					for(TtOrderDetail obj : detail){
						// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
						int toLock = tmClassMapper.lockData(obj.getClassId());
						// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
						if(0 == toLock){
							throw new ServiceException(Message.SYS_BUSY);
						// 否则查询班级剩余报名人数
						}else{
							// 取得商品信息
							TmClass tc = tmClassMapper.selectByPrimaryKey(obj.getClassId());
							int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
							// 提前设置好解锁数据key及解锁状态
							toUpdate.setId(obj.getClassId());
							toUpdate.setLock(Constant.DATA_UNLOCK);
							// 回复一个剩余名额
							toUpdate.setRemainAmount(String.valueOf(remainAmount + 1));
						}
						// 更新班级剩余名额，同时释放乐观锁
						tmClassMapper.updateByPrimaryKeySelective(toUpdate);
					}
				}
				int flag = ttOrderMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "订单表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 删除（根据匹配的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String deleteBySelective( TtOrder record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		TmClass toUpdate = new TmClass();
		try {
			// 查询订单信息
			TtOrder to = ttOrderMapper.selectByPrimaryKey(record.getId());
			// 未完成支付的订单删除需要回滚相应班级剩余名额的数量，即剩余名额+1
			if(Constant.ORDER_STATUS_CANCEL.equals(to.getStatus()) || Constant.ORDER_STATUS_COMMITED.equals(to.getStatus())){
				// 查询关联订单明细
				TtOrderDetail param = new TtOrderDetail();
				param.setId(record.getId());
				List<TtOrderDetail> detail = ttOrderDetailMapper.selectBySelective(param);
				// 依次回滚相应班级的
				for(TtOrderDetail obj : detail){
					// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
					int toLock = tmClassMapper.lockData(obj.getClassId());
					// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
					if(0 == toLock){
						return Message.SYS_BUSY;
					// 否则查询班级剩余报名人数
					}else{
						// 取得商品信息
						TmClass tc = tmClassMapper.selectByPrimaryKey(obj.getClassId());
						int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
						// 提前设置好解锁数据key及解锁状态
						toUpdate.setId(obj.getClassId());
						toUpdate.setLock(Constant.DATA_UNLOCK);
						// 回复一个剩余名额
						toUpdate.setRemainAmount(String.valueOf(remainAmount + 1));
					}
					// 更新班级剩余名额，同时释放乐观锁
					tmClassMapper.updateByPrimaryKeySelective(toUpdate);
				}
			}
			// 删除订单
			ttOrderMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("订单表信息删除",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String insert( TtOrder record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = ttOrderMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单表信息添加",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String insertSelective( TtOrder record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = ttOrderMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单表信息添加",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 修改 （结算与反结算）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String updateByPrimaryKeySelective( TtOrder record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			// 支付完成后在相应班级里(班级学生关联表)加入该学生的数据
			if(Constant.ORDER_STATUS_PAID_SUCCESS.equals(record.getStatus())){
				// 重复结算校验
				String orderCurStatus = ttOrderMapper.selectByPrimaryKey(record.getId()).getStatus();
				if(Constant.ORDER_STATUS_PAID_SUCCESS.equals(orderCurStatus)){
					return Message.PAIDFAILED_REASON + Message.HAS_BALANCE;
				}
				// 通过订单id获取订单信息
				TtOrder orderInfo = ttOrderMapper.selectByPrimaryKey(record.getId());
				TtClassStudent tcs = new TtClassStudent();
				tcs.setCreateBy(record.getUpdateBy());
				tcs.setUpdateBy(record.getUpdateBy());
				tcs.setStudentId(orderInfo.getStudentId());
				// 根据订单id查询相关的班级信息
				TtOrderDetail tod = new TtOrderDetail();
				tod.setId(record.getId());
				List<TtOrderDetail> detailList = ttOrderDetailMapper.selectBySelective(tod);
				// 要处理的班级循环加锁
				for(TtOrderDetail obj : detailList){
					// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
					int toLock = tmClassMapper.lockData(obj.getClassId());
					// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
					if(0 == toLock){
						throw new ServiceException(Message.SYS_BUSY);
					}
				}
				// 默认为该订单中的所有班级已经结算，则循环在订单中的各个班级插入该同学的数据
				for(TtOrderDetail obj : detailList){
					tcs.setId(Sequence.nextId());
					tcs.setClassId(obj.getClassId());
					// 重复报班校验
					TtClassStudent selParam = new TtClassStudent();
					selParam.setStudentId(orderInfo.getStudentId());
					selParam.setClassId(obj.getClassId());
					List<TtClassStudent> hasInClassList = ttClassStudentMapper.selectBySelective(selParam);
					if(hasInClassList != null && hasInClassList.size() > 0){
						throw new ServiceException(Message.PAIDFAILED_REASON + orderInfo.getStudentId() + Message.HAS_IN_CLASS);
					}
					int count = ttClassStudentMapper.insertSelective(tcs);
					// 插入失败校验
					if(count != 1){
						throw new ServiceException(Message.PAIDFAILED_REASON + Message.CLASS_ADD_STUDENT_FAILED);
					}
					// 更新班级已付款人数,同时解锁班级
					TmClass tc = new TmClass();
					tc.setId(obj.getClassId());
					tc.setLock(Constant.DATA_UNLOCK);
					TmClass resClass = tmClassMapper.selectByPrimaryKey(obj.getClassId());
					tc.setPaidAmount(String.valueOf((Integer.parseInt(StringUtils.isEmpty(resClass.getPaidAmount())?"0":resClass.getPaidAmount()) + 1)));
					tmClassMapper.updateByPrimaryKeySelective(tc);
				}
				// 反结算时要删除班级学生数据
			}else if(Constant.ORDER_STATUS_COMMITED.equals(record.getStatus())){
				// 根据订单id查询相关的班级信息
				TtOrderDetail tod = new TtOrderDetail();
				tod.setId(record.getId());
				List<TtOrderDetail> detailList = ttOrderDetailMapper.selectBySelective(tod);
				// 通过订单id获取订单信息
				TtOrder orderInfo = ttOrderMapper.selectByPrimaryKey(record.getId());
				// 要处理的班级循环加锁
				for(TtOrderDetail obj : detailList){
					// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
					int toLock = tmClassMapper.lockData(obj.getClassId());
					// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
					if(0 == toLock){
						throw new ServiceException(Message.SYS_BUSY);
					}
				}
				for(TtOrderDetail obj : detailList){
					TtClassStudent tcs = new TtClassStudent();
					tcs.setClassId(obj.getClassId());
					tcs.setStudentId(orderInfo.getStudentId());
					ttClassStudentMapper.deleteBySelective(tcs);
					// 更新班级已付款人数,同时解锁班级
					TmClass tc = new TmClass();
					tc.setId(obj.getClassId());
					tc.setLock(Constant.DATA_UNLOCK);
					TmClass resClass = tmClassMapper.selectByPrimaryKey(obj.getClassId());
					int count = Integer.parseInt(StringUtils.isEmpty(resClass.getPaidAmount())?"0":resClass.getPaidAmount()) - 1;
					tc.setPaidAmount(String.valueOf(count<0?0:count));
					tmClassMapper.updateByPrimaryKeySelective(tc);
				}
			}
			int flag = ttOrderMapper.updateByPrimaryKeySelective(record);
			if(flag > 0){
				resMsg = Message.UPDATE_SUCCESS;
			}else{
				throw new ServiceException(Message.UPDATE_FAILED);
			}
		}catch(Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String updateByPrimaryKey( TtOrder record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = ttOrderMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单表信息修改",e);
		}
		return resMsg;
	}
	
	/**
	 * 
	 * 保存订单信息批处理(购物车数据转移到订单表，订单状态为未提交)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public Map<String, String> saveOrderFromcartBatch(String studentId) throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();
		try{
			// 取得用户购物车所有数据
			List<TmClass> classList = ttShoppingCartMapper.selectByStudentId(studentId);
			// 取得学生信息
			TmStudent ts = tmStudentMapper.selectByPrimaryKey(studentId);
			// 购物车没有数据则直接返回，前端要做购课车中无班级提醒
			if(classList == null || classList.size() == 0){
				return map;
			}
			// 参数准备
			TtOrderDetail temp = new TtOrderDetail();
			Date date = new Date();
			double totalFee = 0;
			// 遍历购物车中商品信息
			for(TmClass obj : classList){
				// 计算订单总金额
				totalFee = totalFee + Double.valueOf(obj.getTuitionFee());
			}
			// 插入订单表
			TtOrder to = new TtOrder();
			String orderId = Sequence.nextId();
			to.setId(orderId);
			to.setStudentId(studentId);
			// 低保户订单金额处理
			if(Constant.LOW_INCOME_YES.equals(ts.getIsLowincome())){
				to.setTotalMoney("0");
			}else{
				to.setTotalMoney(String.valueOf(totalFee));
			}
			to.setStatus(Constant.ORDER_STATUS_COMMITED);
			to.setCreateBy("studentId:" + studentId);
			to.setUpdateDate(date);
			to.setUpdateBy("studentId:" + studentId);
			to.setCreateDate(date);
			ttOrderMapper.insertSelective(to);
			// 分别插入订单详情表
			for(TmClass obj : classList){
				temp.setId(orderId);
				temp.setClassId(obj.getId());
				ttOrderDetailMapper.insert(temp);
			}
			// 清空用户购物车
			TtShoppingCart tsc = new TtShoppingCart();
			tsc.setStudentId(studentId);
			ttShoppingCartMapper.deleteBySelective(tsc);
			map.put("orderId", orderId);
			// 低保户订单金额处理
			if(Constant.LOW_INCOME_YES.equals(ts.getIsLowincome())){
				map.put("totalFee", "0");
			}else{
				map.put("totalFee", String.valueOf(totalFee));
			}
		}catch(Exception e){
			throw new ServiceException("保存订单信息批处理",e);
		}
		return map;
	}
	
	
	/**
	 * 
	 * 查询（精确匹配有值字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	@Override
	public List<TtOrder> selectExactlyBySelective( TtOrder record ) throws ServiceException {
		try {
			List<TtOrder> returnList = new ArrayList<TtOrder>();
			TtOrderDetail param = new TtOrderDetail();
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("studentId",record.getStudentId());
			params.put("totalMoney",record.getTotalMoney());
			params.put("status",record.getStatus());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			List<TtOrder> list = (List<TtOrder>) getPageList(TtOrderMapper.class, "selectExactlyBySelective",
					params, record.getPage(), record.getPageSize(),record.getOrderSegment());
			// 
			for(TtOrder order : list){
				param.setId(order.getId());
				List<TtOrderDetail> detailList = ttOrderDetailMapper.selectBySelective(param);
				List<TmClass> clasList= new ArrayList<TmClass>();
				for(TtOrderDetail detail : detailList){
					TmClass obj = tmClassMapper.selectByPrimaryKey(detail.getClassId());
					clasList.add(obj);
				}
				order.setOrderDetail(clasList);
				returnList.add(order);
			}
			return returnList;
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得订单表信息",e);
		}
	}
	
}
