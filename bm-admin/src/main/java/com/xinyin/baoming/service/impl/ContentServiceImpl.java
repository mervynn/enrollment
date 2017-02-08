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
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.TmContentMapper;
import com.xinyin.baoming.model.vo.TmContent;
import com.xinyin.baoming.service.IContentService;
import com.xinyin.baoming.util.CommonUtils;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * h5内容表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
public class ContentServiceImpl extends BaseService implements IContentService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2356368264882737546L;
	@Autowired
	private TmContentMapper operationContentMapper;

	@SuppressWarnings("unchecked")
	@Override
	public PageList<TmContent> selectContentList(TmContent operationContent)
			throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
		    params.put("id",operationContent.getId());
		    params.put("type",operationContent.getType());
		    params.put("uri",operationContent.getUri());
		    params.put("createBy",operationContent.getCreateBy());
		    params.put("createDateStr",operationContent.getCreateDateStr());
		    params.put("updateBy",operationContent.getUpdateBy());
		    params.put("updateDateStr",operationContent.getUpdateDateStr());
		    params.put("remarks",operationContent.getRemarks()); 
			return (PageList<TmContent>) getPageList(TmContentMapper.class, "selectContentList",
					params, operationContent.getPage(), operationContent.getPageSize(),operationContent.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("内容查询",e);
		}
	}

	@Override
	public TmContent queryContentInfoById(String id) throws ServiceException {
		try {
			return operationContentMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得内容",e);
		}
	}
	
	@Override
	public TmContent selectByType(String name) throws ServiceException {
		try {
			return operationContentMapper.selectByType(name);
		}catch(Exception e){
			throw new ServiceException("通过内容类型取得内容",e);
		}
	}

	@Transactional
	@Override
	public String save(TmContent operationContent) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			operationContent.setId(Sequence.nextId());
			Date date = new Date();
			operationContent.setUpdateDate(date); // 更新时间
			operationContent.setCreateDate(date); // 创建时间
			// 通过内容url设定精简的html内容
			String html = CommonUtils.getMajorHtmlByUri(operationContent.getUri());
			operationContent.setContent(html);
			operationContent.setUri(Constant.DOMAIN_NAME + Constant.API_START_URI
					+ "content/" + operationContent.getId() + Constant.SUFFIX);
			flag = operationContentMapper.insert(operationContent);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("内容添加",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String modify(TmContent operationContent) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			Date date = new Date();
			operationContent.setUpdateDate(date); // 更新时间
			// 通过内容url设定精简的html内容
			String html = CommonUtils.getMajorHtmlByUri(operationContent.getUri());
			operationContent.setContent(html);
			operationContent.setUri(Constant.DOMAIN_NAME + Constant.API_START_URI
					+ "content/" + operationContent.getId() + Constant.SUFFIX);
			flag = operationContentMapper.updateByPrimaryKey(operationContent);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("内容修改",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String deleteContent(String id) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				int flag = operationContentMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "内容:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("内容删除",e);
		}
		return resMsg;
	}

}
