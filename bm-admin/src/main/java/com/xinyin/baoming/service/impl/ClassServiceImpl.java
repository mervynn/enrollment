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
import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.service.IClassService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 班级表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class ClassServiceImpl extends BaseService implements IClassService {

	@Autowired
	private TmClassMapper tmClassMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmClass>  selectBySelective( TmClass record ) throws ServiceException {
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
			return (PageList<TmClass>) getPageList(TmClassMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("班级表信息查询",e);
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
	public List<TmClass> selectExactlyBySelective( TmClass record ) throws ServiceException {
		try {
			return tmClassMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得班级表信息",e);
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
	public TmClass selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return tmClassMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得班级表信息",e);
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
				int flag = tmClassMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "班级表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("班级表信息删除",e);
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
	public String deleteBySelective( TmClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			tmClassMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("班级表信息删除",e);
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
	public String insert( TmClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmClassMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级表信息添加",e);
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
	public String insertSelective( TmClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmClassMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级表信息添加",e);
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
	public String updateByPrimaryKeySelective( TmClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmClassMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级表信息修改",e);
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
	public String updateByPrimaryKey( TmClass record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmClassMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级表信息修改",e);
		}
		return resMsg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmClass>  selectBySelectiveForWap( TmClass record ) throws ServiceException {
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
			// 辅助参数
			params.put("idListStr",record.getIdListStr());
			return (PageList<TmClass>) getPageList(TmClassMapper.class, "selectBySelectiveForWap",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("wap端班级表信息查询异常",e);
		}
	}
	
	@Transactional
	@Override
	public String upDownClass( String id , String sellFlg, String person) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			// 更新参数准备
			TmClass param = new TmClass();
			param.setStatus(sellFlg);
			param.setUpdateDate(new Date());
			param.setUpdateBy(person);
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				param.setId(key);
				int flag = tmClassMapper.updateByPrimaryKeySelective(param);
				if(!(flag > 0)){
					throw new ServiceException(Message.UPDATE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			// 返回消息设定
			if( Constant.COMMODITY_STATUS_UP.equals(sellFlg)){
				resMsg = msgIds + Message.ON_SELL_SUCCESS;
			}else{
				resMsg = msgIds + Message.STOP_SELL_SUCCESS;
			}
		}catch(Exception e){
			throw new ServiceException("班级表信息更新异常(上下架)",e);
		}
		return resMsg;
	}
	
	/**
	 * 通过类别id列表查询相关班级信息
	 * @param typeList
	 * @return
	 */
	public List<TmClass> selectByClassTypeList(List<String> typeList) throws ServiceException{
		return tmClassMapper.selectByClassTypeList(typeList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmClass> selectOrderDetail(TmClass record) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			return (PageList<TmClass>) getPageList(TmClassMapper.class, "selectOrderDetail",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("班级表信息查询",e);
		}
	}

}
