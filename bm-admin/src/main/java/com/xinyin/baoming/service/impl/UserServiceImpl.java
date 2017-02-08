package com.xinyin.baoming.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.TsMenuMapper;
import com.xinyin.baoming.mapper.TsUserMapper;
import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsUser;
import com.xinyin.baoming.service.IUserService;
import com.xinyin.baoming.util.MD5Util;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 系统用户表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
public class UserServiceImpl extends BaseService implements IUserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	TsUserMapper adminUserMapper;
	
	@Autowired
	TsMenuMapper menuMapper;
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6706296347835813264L;

	@Override
	public TsUser queryUser(String username,String password) throws ServiceException {
		TsUser user = adminUserMapper.queryByUnameAndPword(username, MD5Util.encode(username, password));
		try {
			if(user == null) {
				return null;
			}
			List<TsMenu> disableJspOpts = menuMapper.selectDisableJspOpt(user);
			Map<String, List<TsMenu>> disableJspOptsMap = new HashMap<String, List<TsMenu>>();
			for (TsMenu menu : disableJspOpts) {
				List<TsMenu> tempMenus = disableJspOptsMap.get(menu.getJspName());
				if(tempMenus == null) {
					tempMenus = new ArrayList<TsMenu>();
					disableJspOptsMap.put(menu.getJspName(), tempMenus);
				}
				tempMenus.add(menu);
			}
			user.setDisableJspOptMap(disableJspOptsMap);
			List<TsMenu> hasMenu = menuMapper.selectUserHasMenu(user.getUsername());
			user.setHasMenu(hasMenu);
		} catch (Exception e) {
			throw new ServiceException("查询用户有权操作的菜单", e);
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<TsUser> selectAdminUserList(TsUser adminUser)
			throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("username",adminUser.getUsername());
			params.put("realName",adminUser.getRealName());
			params.put("phone",adminUser.getPhone());
			params.put("upUser",adminUser.getUpUser());
			params.put("roleId",adminUser.getRoleId());
		    params.put("createBy",adminUser.getCreateBy());
		    params.put("createDateStr",adminUser.getCreateDateStr());
		    params.put("updateBy",adminUser.getUpdateBy());
		    params.put("updateDateStr",adminUser.getUpdateDateStr());
			return (PageList<TsUser>) getPageList(TsUserMapper.class, "selectAdminUserList",
					params, adminUser.getPage(), adminUser.getPageSize(),adminUser.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("系统用户信息查询",e);
		}
	}

	@Override
	public TsUser queryAdminUserInfoById(String id) throws ServiceException {
		try {
			return adminUserMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得系统用户信息",e);
		}
	}

	@Transactional
	@Override
	public String save(TsUser adminUser) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			TsUser userExsitsValid = adminUserMapper.selectByPrimaryKey(adminUser.getUsername().trim());
			if(userExsitsValid != null){
				throw new ServiceException(Message.USER_EXISTS);
			}
			Date date = new Date();
			adminUser.setUpdateDate(date); // 更新时间
			adminUser.setCreateDate(date);
			// 密码设定
			if(StringUtils.isNotBlank(adminUser.getPassword())){
				adminUser.setPassword(MD5Util.encode(adminUser.getUsername().trim(),adminUser.getPassword().trim()));
			}
			flag = adminUserMapper.insert(adminUser);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			logger.error("系统用户信息添加异常",e);
			if(StringUtils.isNotEmpty(e.getMessage())){
				throw new ServiceException(e.getMessage());
			}else{
				throw new ServiceException("系统用户信息添加异常",e);
			}
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String modify(TsUser adminUser) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			Date date = new Date();
			adminUser.setUpdateDate(date); // 更新时间
			// 密码设定
			if(StringUtils.isNotBlank(adminUser.getPassword())){
				adminUser.setPassword(MD5Util.encode(adminUser.getUsername().trim(),adminUser.getPassword().trim()));
			}else{
				adminUser.setPassword(null);
			}
			flag = adminUserMapper.updateByPrimaryKey(adminUser);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			logger.error("系统用户信息修改异常",e);
			throw new ServiceException("系统用户信息修改异常",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String deleteAdminUser(String id) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				int flag = adminUserMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "系统用户:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("系统用户信息删除",e);
		}
		return resMsg;
	}

	@Override
	public List<TsUser> selectAllAdminUserList() {
		return adminUserMapper.selectAllAdminUserList();
	}
}
