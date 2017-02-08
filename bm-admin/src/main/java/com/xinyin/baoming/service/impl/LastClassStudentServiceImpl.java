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
import com.xinyin.baoming.mapper.ThLastClassStudentMapper;
import com.xinyin.baoming.mapper.TmTeacherMapper;
import com.xinyin.baoming.mapper.TtRecommendMapper;
import com.xinyin.baoming.model.vo.ThLastClassStudent;
import com.xinyin.baoming.model.vo.TmTeacher;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.model.vo.TtRecommend;
import com.xinyin.baoming.service.ILastClassStudentService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 班级学生关联历史表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class LastClassStudentServiceImpl extends BaseService implements ILastClassStudentService {

	@Autowired
	private ThLastClassStudentMapper thLastClassStudentMapper;
	
	@Autowired
	private TtRecommendMapper ttRecommendMapper;
	
	@Autowired
	private TmTeacherMapper tmTeacherMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<ThLastClassStudent>  selectBySelective( ThLastClassStudent record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("classId",record.getClassId());
			params.put("studentId",record.getStudentId());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			params.put("idListStr",record.getIdListStr());
			return (PageList<ThLastClassStudent>) getPageList(ThLastClassStudentMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息查询",e);
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
	public ThLastClassStudent selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return thLastClassStudentMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得班级学生关联历史表信息",e);
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
				int flag = thLastClassStudentMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "班级学生关联历史表:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息删除",e);
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
	public String deleteBySelective( ThLastClassStudent record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			thLastClassStudentMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息删除",e);
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
	public String insert( ThLastClassStudent record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = thLastClassStudentMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息添加",e);
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
	public String insertSelective( ThLastClassStudent record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = thLastClassStudentMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息添加",e);
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
	public String updateByPrimaryKeySelective( ThLastClassStudent record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = thLastClassStudentMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息修改",e);
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
	public String updateByPrimaryKey( ThLastClassStudent record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = thLastClassStudentMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("班级学生关联历史表信息修改",e);
		}
		return resMsg;
	}
	
	/**
	 * 
	 * 推荐选课
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	@Transactional
	@Override
	public String recommend( String studentList, String classList , TsUser user ) throws ServiceException{
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray studentArr =  JSONObject.parseArray(studentList);
			JSONArray classArr =  JSONObject.parseArray(classList);
			String msgIds = StringUtils.EMPTY;
			TtRecommend tr = new TtRecommend();
			Date date = new Date();
			tr.setCreateBy(user.getUsername());
			tr.setCreateDate(date);
			tr.setUpdateBy(user.getUsername());
			tr.setUpdateDate(date);
			tr.setIsThroughApproval(Constant.REVIEW_STATUS_WAIT);
			// 通过手机号查找授课老师id
			TmTeacher tt = new TmTeacher();
			tt.setPhonenumber(user.getPhone());
			List<TmTeacher> teacherList = tmTeacherMapper.selectBySelective(tt);
			String teacherId = StringUtils.EMPTY;
			if(teacherList != null && teacherList.size() > 0){
				teacherId = teacherList.get(0).getId();
				tr.setTeacherId(teacherId);
			}
			for (int i = 0; i < studentArr.size(); i++) {
				JSONObject studentJo = (JSONObject) studentArr.get(i);
				String studentName = Tools.toString(studentJo.get("name"));
				String rowId = Tools.toString(studentJo.get("id"));
				// 通过流水id查看对应学生id
				String studentId = thLastClassStudentMapper.selectByPrimaryKey(rowId).getStudentId();
				tr.setStudentId(studentId);
				for(int j = 0; j < classArr.size(); j++){
					JSONObject classJo = (JSONObject) classArr.get(j);
					String className = Tools.toString(classJo.get("name"));
					String classId = Tools.toString(classJo.get("id"));
					// 重复推荐校验
					TtRecommend trParam = new TtRecommend();
					trParam.setStudentId(studentId);
					trParam.setTeacherId(teacherId);
					trParam.setClassId(classId);
					List<TtRecommend> recList = ttRecommendMapper.selectExactlyBySelective(trParam);
					if(recList != null && recList.size() > 0){
						throw new ServiceException(studentName + "→" + className + Message.RECOMMEND_DUPLICATE);
					}
					tr.setClassId(classId);
					tr.setId(Sequence.nextId());
					int flag = ttRecommendMapper.insertSelective(tr);
					if(!(flag > 0)){
						throw new ServiceException(Message.RECOMMEND_FAILED);
					}
					msgIds += StringUtils.isNotEmpty(msgIds) ? Constant.COMMA+ studentName + "→" + className  : studentName + "→" + className ;
				}
			}
			resMsg = "以下:" + msgIds + Message.RECOMMEND_SUCCESS;
		}catch(Exception e){
			throw new ServiceException(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():Message.RECOMMEND_FAILED,e);
		}
		return resMsg;
	}
}
