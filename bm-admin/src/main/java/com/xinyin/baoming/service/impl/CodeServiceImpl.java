package com.xinyin.baoming.service.impl;

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
import com.xinyin.baoming.mapper.TcCodeMapper;
import com.xinyin.baoming.mapper.TmClassMapper;
import com.xinyin.baoming.mapper.TtOrderDetailMapper;
import com.xinyin.baoming.mapper.TtOrderMapper;
import com.xinyin.baoming.mapper.TtShoppingCartMapper;
import com.xinyin.baoming.mapper.TzAllOrderDetailMapper;
import com.xinyin.baoming.mapper.TzAllOrderMapper;
import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TtOrder;
import com.xinyin.baoming.model.vo.TtOrderDetail;
import com.xinyin.baoming.model.vo.TtShoppingCart;
import com.xinyin.baoming.model.vo.TzAllOrder;
import com.xinyin.baoming.model.vo.TzAllOrderDetail;
import com.xinyin.baoming.service.ICodeService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 码表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
public class CodeServiceImpl extends BaseService implements ICodeService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5723931065141110131L;
	@Autowired
	private TcCodeMapper sysCodeMapper;
	
	@Autowired
	private TtShoppingCartMapper ttShoppingCartMapper;
	
	@Autowired
	private TtOrderMapper ttOrderMapper;
	
	@Autowired
	private TtOrderDetailMapper ttOrderDetailMapper;
	
	@Autowired
	private TzAllOrderMapper tzAllOrderMapper;
	
	@Autowired
	private TzAllOrderDetailMapper tzAllOrderDetailMapper;
	
	@Autowired
	private IContextService iContextService;
	
	@Autowired
	private TmClassMapper tmClassMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<TcCode> selectSysCodeList(TcCode sysCodeInfo)
			throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
		    params.put("code",sysCodeInfo.getCode());
		    params.put("sort",sysCodeInfo.getSort());
			params.put("name",sysCodeInfo.getName());
		    params.put("englishName",sysCodeInfo.getEnglishName());
		    params.put("type",sysCodeInfo.getType());
			return (PageList<TcCode>) getPageList(TcCodeMapper.class, "selectSysCodeList",
					params, sysCodeInfo.getPage(), sysCodeInfo.getPageSize(),sysCodeInfo.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("系统常量查询",e);
		}
	}

	@Override
	public TcCode querySysCodeInfoById(String code, String sort) throws ServiceException {
		try {
			return sysCodeMapper.selectByPrimaryKey(new TcCode(code, sort));
		}catch(Exception e){
			throw new ServiceException("通过ID取得系统常量",e);
		}
	}

	@Transactional
	@Override
	public String save(TcCode sysCodeInfo) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			flag = sysCodeMapper.insert(sysCodeInfo);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("系统常量添加",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String modify(TcCode sysCodeInfo) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			// 如果改变招生阶段，则执行相应批处理后(清空购课车，转移订单到历史订单表，再改变码表
			if("0".equals(sysCodeInfo.getCode()) && !sysCodeInfo.getName().equals(iContextService.getCodeList("0").get(0).getName())){
				// 按班级分组求和，查询所有选课车中的课程数据和未完成订单明细数据
				List<TtShoppingCart> cartList = ttShoppingCartMapper.selectByClassGroup();
				// 所有相关班级逻辑加锁
				for(TtShoppingCart tsc : cartList){
					// 更新该商品未被加锁的数据 使其锁状态为 上锁：1
					int toLock = tmClassMapper.lockData(tsc.getClassId());
					// 更新结果为0，则该商品同时操作人数过多，返回繁忙提示
					if(0 == toLock){
						throw new ServiceException(Message.SYS_BUSY);
					}
				}
				// 查询所有已提交（未支付）订单对应的订单明细数据
				TtOrder to = new TtOrder();
				to.setStatus(Constant.ORDER_STATUS_COMMITED);
				List<TtOrder> orderList = ttOrderMapper.selectExactlyBySelective(to);
				// 转入历史订单表
				TzAllOrder tao = new TzAllOrder();
				for(TtOrder temp : orderList){
					tao.setId(temp.getId());
					tao.setCreateBy(temp.getCreateBy());
					tao.setCreateDate(temp.getCreateDate());
					tao.setRemarks(temp.getRemarks());
					tao.setStatus(temp.getStatus());
					tao.setStudentId(temp.getStudentId());
					tao.setTotalMoney(temp.getTotalMoney());
					tao.setUpdateBy(temp.getUpdateBy());
					tao.setUpdateDate(temp.getUpdateDate());
					tzAllOrderMapper.insert(tao);
					TtOrderDetail paramD = new TtOrderDetail();
					paramD.setId(temp.getId());
					List<TtOrderDetail> detailList = ttOrderDetailMapper.selectBySelective(paramD);
					// 转入历史订单明细表
					TzAllOrderDetail taod = new TzAllOrderDetail();
					for(TtOrderDetail tod : detailList){
						taod.setId(tod.getId());
						taod.setClassId(tod.getClassId());
						tzAllOrderDetailMapper.insert(taod);
					}
				}
				// 删除当前阶段订单
				ttOrderMapper.deleteBySelective(to);
				// 删除当前阶段订单明细
				for(TtOrder temp : orderList){
					ttOrderDetailMapper.deleteByPrimaryKey(temp.getId());
				}
				// 清空购课车数据
				ttShoppingCartMapper.deleteAll();
				// 回复相关班级的剩余名额并解锁对应班级
				TmClass tcparam = new TmClass();
				for(TtShoppingCart tsc : cartList){
					tcparam.setId(tsc.getClassId());
					tcparam.setLock(Constant.DATA_UNLOCK);
					TmClass tc = tmClassMapper.selectByPrimaryKey(tsc.getClassId());
					int remainAmount = StringUtils.isEmpty(tc.getRemainAmount())?0:Integer.parseInt(tc.getRemainAmount());
					tcparam.setRemainAmount(String.valueOf(remainAmount + (StringUtils.isEmpty(tsc.getRemarks())?0:Integer.parseInt(tsc.getRemarks()))));
					tmClassMapper.updateByPrimaryKeySelective(tcparam);
				}
			}
			flag = sysCodeMapper.updateByPrimaryKey(sysCodeInfo);
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
	
	@Transactional
	@Override
	public String deleteSysCode(String id) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String code = Tools.toString(jo.get("code"));
				String sort = Tools.toString(jo.get("sort"));
				int flag = sysCodeMapper.deleteByPrimaryKey(new TcCode(code, sort));
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "系统常量:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("系统常量删除",e);
		}
		return resMsg;
	}
}
