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
import com.xinyin.baoming.mapper.ThLastClassMapper;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.ThLastClass;
import com.xinyin.baoming.service.ILastClassService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 历史班级表信息管理服务层
 * 
 * 创建时间： 2016-08-22
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class LastClassServiceImpl extends BaseService implements ILastClassService {

	@Autowired
	private ThLastClassMapper thLastClassMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<ThLastClass>  selectBySelective( ThLastClass record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("code",record.getCode());
			params.put("minAge",record.getMinAge());
			params.put("isOnlyOldStudent",record.getIsOnlyOldStudent());
			params.put("targetGroup",record.getTargetGroup());
			params.put("limitedAmount",record.getLimitedAmount());
			params.put("defaultAmount",record.getDefaultAmount());
			params.put("remainAmount",record.getRemainAmount());
			params.put("paidAmount",record.getPaidAmount());
			params.put("status",record.getStatus());
			params.put("tuitionFee",record.getTuitionFee());
			params.put("classTimeWeek",record.getClassTimeWeek());
			params.put("classTimeBegin",record.getClassTimeBegin());
			params.put("classTimeEnd",record.getClassTimeEnd());
			params.put("picture",record.getPicture());
			params.put("classTypeId",record.getClassTypeId());
			params.put("teacherId",record.getTeacherId());
			params.put("classroomId",record.getClassroomId());
			params.put("termId",record.getTermId());
			params.put("lock",record.getLock());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			params.put("idListStr",record.getIdListStr());
			return (PageList<ThLastClass>) getPageList(ThLastClassMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("历史班级表信息查询",e);
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
	public List<ThLastClass> selectExactlyBySelective( ThLastClass record ) throws ServiceException {
		try {
			return thLastClassMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得历史班级表信息",e);
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
	public ThLastClass selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return thLastClassMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得历史班级表信息",e);
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
				int flag = thLastClassMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "历史班级表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("历史班级表信息删除",e);
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
	public String deleteBySelective( ThLastClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			thLastClassMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("历史班级表信息删除",e);
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
	public String insert( ThLastClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = thLastClassMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("历史班级表信息添加",e);
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
	public String insertSelective( ThLastClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = thLastClassMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("历史班级表信息添加",e);
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
	public String updateByPrimaryKeySelective( ThLastClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = thLastClassMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("历史班级表信息修改",e);
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
	public String updateByPrimaryKey( ThLastClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = thLastClassMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("历史班级表信息修改",e);
		}
		return resMsg;
	}
}
