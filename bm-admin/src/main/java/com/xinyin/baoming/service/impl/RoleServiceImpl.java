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
import com.xinyin.baoming.mapper.TsRoleMapper;
import com.xinyin.baoming.model.vo.TsRole;
import com.xinyin.baoming.model.vo.TsRoleAuth;
import com.xinyin.baoming.service.IRoleService;
import com.xinyin.baoming.util.Sequence;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

/**
 * 
 * 角色表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
public class RoleServiceImpl extends BaseService implements IRoleService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2029787080630394577L;
	
	@Autowired
	TsRoleMapper authRoleMapper;

	@Override
	public List<TsRole> selectAllRoles() {
		return authRoleMapper.selectAllRoles();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList<TsRole> selectAuthRoleList(TsRole authRole)
			throws ServiceException {
		try {
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("roleId",authRole.getRoleId());
			params.put("roleName",authRole.getRoleName());
		    params.put("createBy",authRole.getCreateBy());
		    params.put("createDateStr",authRole.getCreateDateStr());
		    params.put("updateBy",authRole.getUpdateBy());
		    params.put("updateDateStr",authRole.getUpdateDateStr());
			return (PageList<TsRole>) getPageList(TsRoleMapper.class, "selectAuthRoleList",
					params, authRole.getPage(), authRole.getPageSize(),authRole.getOrderSegment());
		}catch(Exception e){
			throw new ServiceException("角色信息查询",e);
		}
	}

	@Override
	public TsRole queryAuthRoleInfoById(String id) throws ServiceException {
		try {
			return authRoleMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			throw new ServiceException("通过ID取得角色信息",e);
		}
	}

	@Transactional
	@Override
	public String save(TsRole authRole) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			authRole.setRoleId(Sequence.nextId());
			Date date = new Date();
			authRole.setUpdateDate(date); // 更新时间
			authRole.setCreateDate(date);
			flag = authRoleMapper.insert(authRole);
			resMsg = flag > 0 ? Message.SAVE_SUCCESS : Message.SAVE_FAILED;
		}catch(Exception e){
			throw new ServiceException("角色信息添加",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String modify(TsRole authRole) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		int flag = 0;
		try {
			Date date = new Date();
			authRole.setUpdateDate(date); // 更新时间
			flag = authRoleMapper.updateByPrimaryKey(authRole);
			resMsg = flag > 0 ? Message.UPDATE_SUCCESS : Message.UPDATE_FAILED;
		}catch(Exception e){
			throw new ServiceException("角色信息修改",e);
		}
		return resMsg;
	}
	
	@Transactional
	@Override
	public String deleteAuthRole(String id) throws ServiceException {
		String resMsg = StringUtils.EMPTY;
		try {
			JSONArray arr =  JSONObject.parseArray(id);
			// 拼装删除消息主键
			String msgIds = StringUtils.EMPTY;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject jo = (JSONObject) arr.get(i);
				String name = Tools.toString(jo.get("name"));
				String key = Tools.toString(jo.get("id"));
				int flag = authRoleMapper.deleteByPrimaryKey(key);
				if(!(flag > 0)){
					throw new ServiceException(Message.DELETE_FAILED);
				}
				msgIds += StringUtils.isNotEmpty(msgIds)?Constant.COMMA+ name:name;
			}
			resMsg = "角色:" + msgIds + Message.DELETE_SUCCESS;
		}catch(Exception e){
			throw new ServiceException("角色信息删除",e);
		}
		return resMsg;
	}

	@Override
	public List<TsRoleAuth> selectAuthRoleRules(String roleId) {
		return authRoleMapper.selectAuthRoleRules(roleId);
	}
	
	@Transactional
	public String modifyRoleAuth(String roleId, String ids) throws ServiceException {
		String resMsg = "授权成功！";
		String[] idsArr = ids.split(",");
		if(idsArr.length > 0 ) {
			try {
				authRoleMapper.deleteRoleAuth(roleId);
			} catch (Exception e) {
				throw new ServiceException("删除角色权限", e);
			}
			TsRoleAuth rule = new TsRoleAuth();
			rule.setRoleId(roleId);
			for (String menuId : idsArr) {
				rule.setMenuId(menuId);
				try {
					authRoleMapper.insertRoleAuth(rule);
				} catch (Exception e) {
					throw new ServiceException("插入角色权限", e);
				}
			}
		}
		return resMsg;
	}
}
