package com.xinyin.baoming.service.impl;

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
import com.xinyin.baoming.mapper.TtOrderMapper;
import com.xinyin.baoming.mapper.TtShoppingCartMapper;
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.service.IShoppingCartService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 购课车表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class ShoppingCartServiceImpl extends BaseService implements IShoppingCartService {

	@Autowired
	private TtShoppingCartMapper ttShoppingCartMapper;
	
	@Autowired
	private TmClassMapper tmClassMapper;
	
	@Autowired
	private TtOrderMapper ttOrderMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TtShoppingCart>  selectBySelective( TtShoppingCart record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("studentId",record.getStudentId());
			params.put("classId",record.getClassId());
			params.put("operationTeacherId",record.getOperationTeacherId());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			return (PageList<TtShoppingCart>) getPageList(TtShoppingCartMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("购课车表信息查询",e);
		}
	}
	
	/**
	 * 
	 * 查询（精确匹配有值字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Override
	public List<TtShoppingCart> selectExactlyBySelective( TtShoppingCart record ) throws ServiceException {
		try {
			return ttShoppingCartMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得购课车表信息",e);
		}
	}
	
	/**
	 * 
	 * 通过学生id查询相关订单信息(商品列表list)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	public List<TmClass> selectByStudentId ( String studentId ){
		try {
			return ttShoppingCartMapper.selectByStudentId(studentId);
		}catch(Exception e){
			throw new ServiceException("通过学生ID取得购课车详细信息",e);
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
	public TtShoppingCart selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return ttShoppingCartMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得购课车表信息",e);
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
				TtShoppingCart tsc = new TtShoppingCart();
				TtShoppingCart tscRes = ttShoppingCartMapper.selectByPrimaryKey(key);
				tsc.setStudentId(tscRes.getStudentId());
				tsc.setClassId(tscRes.getClassId());
				// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
				int toLock = tmClassMapper.lockData(tsc.getClassId());
				// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
				if(0 == toLock){
					throw new ServiceException(Message.SYS_BUSY);
				// 否则查询班级剩余报名人数
				}else{
					// 取得商品信息
					TmClass tc = tmClassMapper.selectByPrimaryKey(tsc.getClassId());
					int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
					// 提前设置好解锁数据key及解锁状态
					TmClass toUpdate = new TmClass();
					toUpdate.setId(tsc.getClassId());
					toUpdate.setLock(Constant.DATA_UNLOCK);
					// 回复一个剩余名额
					toUpdate.setRemainAmount(String.valueOf(remainAmount + 1));
					ttShoppingCartMapper.deleteBySelective(tsc);
					// 更新班级剩余名额，同时释放乐观锁
					tmClassMapper.updateByPrimaryKeySelective(toUpdate);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "购课车表:" + msgIds + Message.DELETE_SUCCESS;
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
	public String deleteBySelective( TtShoppingCart record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		TmClass toUpdate = new TmClass();
		try {
			// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
			int toLock = tmClassMapper.lockData(record.getClassId());
			// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
			if(0 == toLock){
				return Message.SYS_BUSY;
			// 否则查询班级剩余报名人数
			}else{
				// 取得商品信息
				TmClass tc = tmClassMapper.selectByPrimaryKey(record.getClassId());
				int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
				// 提前设置好解锁数据key及解锁状态
				toUpdate.setId(record.getClassId());
				toUpdate.setLock(Constant.DATA_UNLOCK);
				// 回复一个剩余名额
				toUpdate.setRemainAmount(String.valueOf(remainAmount + 1));
			}
			ttShoppingCartMapper.deleteBySelective(record);
			// 更新班级剩余名额，同时释放乐观锁
			tmClassMapper.updateByPrimaryKeySelective(toUpdate);
		}catch(Exception e){
			// 更新班级剩余名额，同时释放乐观锁
			tmClassMapper.updateByPrimaryKeySelective(toUpdate);
			throw new ServiceException("购课车表信息删除",e);
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
	public String insert( TtShoppingCart record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = ttShoppingCartMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("购课车表信息添加",e);
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
	 * 
	 * 注意：添加订单在这里先配置为事务性，如果效率不高可改为去掉该注解（Transactional）
	 **/
	@Transactional
	@Override
	public String insertSelective( TtShoppingCart record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		TmClass toUpdate = new TmClass();
		int flag = 0;
		try{
			// 重复提交校验
			List<TtShoppingCart> list = ttShoppingCartMapper.selectExactlyBySelective(record);
			if(list.size() != 0){
				return Message.RESUBMIT;
			}
			// 已支付和待支付的订单查询
			List<TtOrder> orderList = ttOrderMapper.selectByStudentAndClass(record.getStudentId(), record.getClassId());
			for(TtOrder obj : orderList){
				// 您有该班级的订单尚未支付
				if(Constant.ORDER_STATUS_COMMITED.equals(obj.getStatus())){
					return Message.HAS_ORDER_TODO;
				}
				// 您已经购买了该班级
				if(Constant.ORDER_STATUS_PAID_SUCCESS.equals(obj.getStatus())){
					return Message.HAS_BOUGHT;
				}
			}
			// 与购物车已选课程时间冲突校验
			TmClass curClass = tmClassMapper.selectByPrimaryKey(record.getClassId());
			List<TmClass> tcList = ttShoppingCartMapper.selectByStudentId(record.getStudentId());
			for(TmClass tc : tcList){
				if(tc.getClassTimeWeek().equals(curClass.getClassTimeWeek()) 
					&& ((tc.getClassTimeBegin().compareTo(curClass.getClassTimeBegin()) >= 0 && (tc.getClassTimeBegin().compareTo(curClass.getClassTimeEnd()) < 0)) 
							|| (tc.getClassTimeEnd().compareTo(curClass.getClassTimeEnd()) >= 0 && (tc.getClassTimeEnd().compareTo(curClass.getClassTimeBegin()) < 0)))){
					return Message.CONFLICT_WITH_CART;
				}
			}
			// 与已提交和已付款订单时间冲突校验
			List<TmClass> toList = tmClassMapper.selectOrderDetailByStudentId(record.getStudentId());
			for(TmClass tc : toList){
				if(tc.getClassTimeWeek().equals(curClass.getClassTimeWeek()) 
						&& ((tc.getClassTimeBegin().compareTo(curClass.getClassTimeBegin()) >= 0 && (tc.getClassTimeBegin().compareTo(curClass.getClassTimeEnd()) < 0)) 
								|| (tc.getClassTimeEnd().compareTo(curClass.getClassTimeEnd()) >= 0 && (tc.getClassTimeEnd().compareTo(curClass.getClassTimeBegin()) < 0)))){
						return Message.CONFLICT_WITH_ORDER;
					}
			}
			// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
			int toLock = tmClassMapper.lockData(record.getClassId());
			// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
			if(0 == toLock){
				//throw new ServiceException(Message.SYS_BUSY);
				return Message.SYS_BUSY;
			// 否则查询班级剩余报名人数
			}else{
				// 提前设置好解锁数据key及解锁状态
				toUpdate.setId(record.getClassId());
				toUpdate.setLock(Constant.DATA_UNLOCK);
				TmClass tc = tmClassMapper.selectByPrimaryKey(record.getClassId());
				int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
				// 人数大于0时候
				if(0 < remainAmount){
					int limitedAmount =  Integer.parseInt(StringUtils.isEmpty(tc.getLimitedAmount())?"0":tc.getLimitedAmount());
					// 计算后的剩余名额
					int computeRemainAmount = 0;
					// 判断剩余人数和总名额关系，相等则更新该锁定的商品的剩余人数=总名额-预设名额-1
					if(remainAmount == limitedAmount){
						computeRemainAmount = limitedAmount - Integer.parseInt(StringUtils.isEmpty(tc.getDefaultAmount())?"0":tc.getDefaultAmount()) -1;
					}else{
						// 否则剩余人数=剩余人数-1
						computeRemainAmount = remainAmount - 1;
					}
					// 小于0的情况 设定为0
					if(computeRemainAmount < 0){
						computeRemainAmount = 0;
					}
					toUpdate.setRemainAmount(String.valueOf(computeRemainAmount));
					// 购物车添加商品
					record.setId(Sequence.nextId());
					Date date = new Date();
					record.setUpdateDate(date); // 更新时间
					record.setCreateDate(date); // 创建时间
					flag = ttShoppingCartMapper.insertSelective(record);
					resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
				// 否则返回班级已经报满提示
				}else{
					resMsg =  Message.CLASS_IS_FULL;
				}
			}
			// 更新班级剩余名额，同时释放乐观锁
			tmClassMapper.updateByPrimaryKeySelective(toUpdate);
		}catch(Exception e){
			// 由于Transactional,所以不要释放乐观锁就可以回滚。如若去掉Transactional一定要恢复下面的代码
			// tmClassMapper.updateByPrimaryKeySelective(toUpdate);
			throw new ServiceException("购课车表信息添加",e);
		}
		return resMsg;
	}

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String updateByPrimaryKeySelective( TtShoppingCart record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = ttShoppingCartMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("购课车表信息修改",e);
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
	public String updateByPrimaryKey( TtShoppingCart record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = ttShoppingCartMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("购课车表信息修改",e);
		}
		return resMsg;
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	@Override
	public List<TtShoppingCart> selectShoppingCartByStudentId ( String studentId ) throws ServiceException{
		return ttShoppingCartMapper.selectShoppingCartByStudentId(studentId);
	}
}
