package com.xinyin.baoming.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.mapper.TtRecommendMapper;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TtRecommend;
import com.xinyin.baoming.service.IRecommendService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 旧生课程推荐表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class RecommendServiceImpl extends BaseService implements IRecommendService {

	@Autowired
	private TtRecommendMapper ttRecommendMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TtRecommend>  selectBySelective( TtRecommend record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("studentId",record.getStudentId());
			params.put("classId",record.getClassId());
			params.put("teacherId",record.getTeacherId());
			params.put("isThroughApproval",record.getIsThroughApproval());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			return (PageList<TtRecommend>) getPageList(TtRecommendMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息查询",e);
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
	public TtRecommend selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return ttRecommendMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得旧生课程推荐表信息",e);
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
				int flag = ttRecommendMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "旧生课程推荐表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息删除",e);
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
	public String deleteBySelective( TtRecommend record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			ttRecommendMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息删除",e);
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
	public String insert( TtRecommend record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = ttRecommendMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息添加",e);
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
	public String insertSelective( TtRecommend record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = ttRecommendMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息添加",e);
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
	public String updateByPrimaryKeySelective( TtRecommend record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = ttRecommendMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.OPERATION_SUCCESS : Message.OPERATION_FAILED;
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息修改",e);
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
	public String updateByPrimaryKey( TtRecommend record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = ttRecommendMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("旧生课程推荐表信息修改",e);
		}
		return resMsg;
	}
}
