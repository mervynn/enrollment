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
import com.xinyin.baoming.mapper.TmTeacherMapper;
import com.xinyin.baoming.mapper.TsUserMapper;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.model.vo.TmTeacher;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.ITeacherService;
import com.xinyin.baoming.service.context.IContextService;
import com.xinyin.baoming.util.MD5Util;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 授课老师信息信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
@SuppressWarnings("serial")
public class TeacherServiceImpl extends BaseService implements ITeacherService {

	@Autowired
	private TmTeacherMapper tmTeacherMapper;
	
	@Autowired
	private TsUserMapper tsUserMapper;
	
	@Autowired
	private IContextService iContextService;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmTeacher>  selectBySelective( TmTeacher record ) throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("id",record.getId());
			params.put("idcardNo",record.getIdcardNo());
			params.put("phonenumber",record.getPhonenumber());
			params.put("name",record.getName());
			params.put("sex",record.getSex());
			params.put("pictureUrl",record.getPictureUrl());
			params.put("createBy",record.getCreateBy());
			params.put("createDateStr",record.getCreateDateStr());
			params.put("updateBy",record.getUpdateBy());
			params.put("updateDateStr",record.getUpdateDateStr());
			params.put("remarks",record.getRemarks());
			return (PageList<TmTeacher>) getPageList(TmTeacherMapper.class, "selectBySelective",
				params, record.getPage(), record.getPageSize(),record.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息查询",e);
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
	public List<TmTeacher> selectExactlyBySelective( TmTeacher record ) throws ServiceException {
		try {
			return tmTeacherMapper.selectExactlyBySelective(record);
		}catch(Exception e){
			throw new ServiceException("通过精确条件取得授课老师信息信息",e);
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
	public TmTeacher selectByPrimaryKey( String id ) throws ServiceException {
		try {
			return tmTeacherMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得授课老师信息信息",e);
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
				int flag = tmTeacherMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "授课老师信息:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息删除",e);
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
	public String deleteBySelective( TmTeacher record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			tmTeacherMapper.deleteBySelective(record);
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息删除",e);
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
	public String insert( TmTeacher record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmTeacherMapper.insert(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息添加",e);
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
	public String insertSelective( TmTeacher record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			record.setId(Sequence.nextId());
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			record.setCreateDate(date); // 创建时间
			flag = tmTeacherMapper.insertSelective(record);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息添加",e);
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
	public String updateByPrimaryKeySelective( TmTeacher record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmTeacherMapper.updateByPrimaryKeySelective(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息修改",e);
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
	public String updateByPrimaryKey( TmTeacher record ) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try{
			Date date = new Date();
			record.setUpdateDate(date); // 更新时间
			flag = tmTeacherMapper.updateByPrimaryKey(record);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("授课老师信息信息修改",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String authorization( String id ,String person) throws ServiceException{
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 准备插入参数
			TsUser tu = new TsUser();
			// 默认密码
			String defaultPassword = null;
			// 获取老师默认登陆密码和老师角色ID
			for(TcCode tc : iContextService.getCodeList("10")){
				if("1".equals(tc.getSort())){
					tu.setRoleId(tc.getName());
				}else{
					defaultPassword = tc.getName();
				}
			}
			Date date = new Date();
			tu.setCreateBy(person);
			tu.setCreateDate(date);
			tu.setUpdateBy(person);
			tu.setUpdateDate(date);
			// 拼装授权消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				TmTeacher tt = tmTeacherMapper.selectByPrimaryKey(key);
				// 重复导入校验
				TsUser tuParam = new TsUser();
				tuParam.setPhone(tt.getPhonenumber());
				List<TsUser> validDate = tsUserMapper.selectAdminUserList(tuParam);
				if(validDate != null && validDate.size() > 0){
					throw new ServiceException(tt.getPhonenumber() + Message.IMPORT_DUPLICATE);
				}
				tu.setUsername(tt.getPhonenumber());
				tu.setPassword(MD5Util.encode(tt.getPhonenumber(), defaultPassword));
				tu.setRealName(tt.getName());
				tu.setPhone(tt.getPhonenumber());
				int flag = tsUserMapper.insert(tu);
				if(!(flag > 0)){
					throw new ServiceException(Message.AUTHRIZATION_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "授课老师:" + msgIds + Message.AUTHRIZATION_SUCCESS;
		}catch(Exception e){
			throw new ServiceException(StringUtils.isNotEmpty(e.getMessage())?e.getMessage():"授课老师授权", e);
		}
		return resMsg;
		
	}
}
