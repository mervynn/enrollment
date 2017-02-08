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
import com.xinyin.baoming.mapper.TmClassroomMapper;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TmClassroom;
import com.xinyin.baoming.service.IClassroomService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 教室信息信息管理服务层
 * 
 * 创建时间： 2016-08-25
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class ClassroomServiceImpl extends BaseService implements IClassroomService {

	@Autowired
	private TmClassroomMapper tmClassroomMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmClassroom>  selectBySelective( TmClassroom record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("classroomNo",record.getClassroomNo());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			return (PageList<TmClassroom>) getPageList(TmClassroomMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("教室信息信息查询",e);
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
	public List<TmClassroom> selectExactlyBySelective( TmClassroom record ) throws ServiceException {
		try {
			return tmClassroomMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得教室信息信息",e);
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
	public TmClassroom selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return tmClassroomMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得教室信息信息",e);
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
				int flag = tmClassroomMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "教室信息:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("教室信息信息删除",e);
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
	public String deleteBySelective( TmClassroom record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			tmClassroomMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("教室信息信息删除",e);
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
	public String insert( TmClassroom record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmClassroomMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("教室信息信息添加",e);
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
	public String insertSelective( TmClassroom record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmClassroomMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("教室信息信息添加",e);
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
	public String updateByPrimaryKeySelective( TmClassroom record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmClassroomMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("教室信息信息修改",e);
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
	public String updateByPrimaryKey( TmClassroom record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmClassroomMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("教室信息信息修改",e);
		}
		return resMsg;
	}
}
