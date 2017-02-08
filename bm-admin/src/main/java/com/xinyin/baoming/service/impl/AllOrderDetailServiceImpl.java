package com.xinyin.baoming.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.TzAllOrderDetailMapper;
import com.xinyin.baoming.model.vo.TzAllOrderDetail;
import com.xinyin.baoming.service.IAllOrderDetailService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 订单明细归档表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class AllOrderDetailServiceImpl extends BaseService implements IAllOrderDetailService {

	@Autowired
	private TzAllOrderDetailMapper tzAllOrderDetailMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TzAllOrderDetail>  selectBySelective( TzAllOrderDetail record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("classId",record.getClassId());
			return (PageList<TzAllOrderDetail>) getPageList(TzAllOrderDetailMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息查询",e);
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
	public TzAllOrderDetail selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return tzAllOrderDetailMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得订单明细归档表信息",e);
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
				int flag = tzAllOrderDetailMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "订单明细归档表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息删除",e);
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
	public String deleteBySelective( TzAllOrderDetail record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			tzAllOrderDetailMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息删除",e);
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
	public String insert( TzAllOrderDetail record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			flag = tzAllOrderDetailMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息添加",e);
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
	public String insertSelective( TzAllOrderDetail record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			flag = tzAllOrderDetailMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息添加",e);
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
	public String updateByPrimaryKeySelective( TzAllOrderDetail record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			flag = tzAllOrderDetailMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息修改",e);
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
	public String updateByPrimaryKey( TzAllOrderDetail record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			flag = tzAllOrderDetailMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("订单明细归档表信息修改",e);
		}
		return resMsg;
	}
}
