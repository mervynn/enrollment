package com.gh.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gh.dao.SysConfigQuartzMapper;
import com.gh.exception.ServiceException;
import com.gh.model.vo.SysConfigQuartz;
import com.gh.service.ISysConfigQuartzService;
import com.gh.util.CommonUtils;
import com.gh.util.Tools;
import com.gh.util.constant.Constant;
import com.gh.util.constant.Message;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 计划任务管理服务
 * 
 * @author HeMingwei
 *
 */
@Service("sysConfigQuartzService")
public class SysConfigQuartzServiceImpl extends BaseService implements ISysConfigQuartzService {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5723931065141110131L;
	@Autowired
	private SysConfigQuartzMapper sysConfigQuartzMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<SysConfigQuartz> selectConfigQuartzList(SysConfigQuartz sysConfigQuartz)
			throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
		    params.put("id",sysConfigQuartz.getId());
		    params.put("triggerName",sysConfigQuartz.getTriggerName());
			params.put("cronExpression",sysConfigQuartz.getCronExpression());
			params.put("jobDetailName",sysConfigQuartz.getJobDetailName());
			params.put("targetObject",sysConfigQuartz.getTargetObject());
			params.put("methodName",sysConfigQuartz.getMethodName());
			params.put("concurrent",sysConfigQuartz.getConcurrent());
			params.put("state",sysConfigQuartz.getState());
			params.put("isSpringBean",sysConfigQuartz.getIsSpringBean());
		    params.put("createBy",sysConfigQuartz.getCreateBy());
		    params.put("createDateStr",sysConfigQuartz.getCreateDateStr());
		    params.put("updateBy",sysConfigQuartz.getUpdateBy());
		    params.put("updateDateStr",sysConfigQuartz.getUpdateDateStr());
		    params.put("remarks",sysConfigQuartz.getRemarks()); 
			return (PageList<SysConfigQuartz>) getPageList(SysConfigQuartzMapper.class, "selectSysConfigQuartzList",
					params, sysConfigQuartz.getPage(), sysConfigQuartz.getPageSize(),sysConfigQuartz.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("计划任务查询",e);
		}
	}

	@Override
	public SysConfigQuartz queryConfigQuartzInfoById(String id) throws ServiceException {
		try {
			return sysConfigQuartzMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得计划任务",e);
		}
	}

	@Transactional
	@Override
	public String save(SysConfigQuartz sysConfigQuartz) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			sysConfigQuartz.setId(CommonUtils.nextId());
			Date date = new Date();
			sysConfigQuartz.setUpdateDate(date); // 更新时间
			sysConfigQuartz.setCreateDate(date); // 创建时间
			flag = sysConfigQuartzMapper.insert(sysConfigQuartz);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("计划任务添加",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String modify(SysConfigQuartz sysConfigQuartz) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			Date date = new Date();
			sysConfigQuartz.setUpdateDate(date); // 更新时间
			flag = sysConfigQuartzMapper.updateByPrimaryKey(sysConfigQuartz);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("计划任务修改",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String deleteConfigQuartz(String id) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				int flag = sysConfigQuartzMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "计划任务:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("计划任务删除",e);
		}
		return resMsg;
	}

}
