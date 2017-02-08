package com.xinyin.baoming.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TsRole;
import com.xinyin.baoming.model.vo.TsRoleAuth;

/**
 * 
 * 角色表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IRoleService{

	/**
	 * 角色信息查询
	 * @param authRole
	 * @return
	 * @throws ServiceException
	 */
	PageList<TsRole> selectAuthRoleList(TsRole authRole) throws ServiceException;
	
	/**
	 * 通过ID取得角色信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	TsRole queryAuthRoleInfoById(String id) throws ServiceException;
	
	/**
	 * 角色信息保存
	 * @param authRole
	 * @return
	 * @throws ServiceException
	 */
	String save(TsRole authRole)  throws ServiceException;
	
	/**
	 * 角色信息修改
	 * @param authRole
	 * @return
	 * @throws ServiceException
	 */
	String modify(TsRole authRole)  throws ServiceException;
	
	/**
	 * 角色信息删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	String deleteAuthRole(String id)  throws ServiceException;
	
	/**
	 * 取得全部角色信息
	 * 
	 * @return
	 */
	List<TsRole> selectAllRoles();
	
	/**
	 * 取得全部角色规则
	 * 
	 * @param roleId
	 * @return
	 */
	List<TsRoleAuth> selectAuthRoleRules(String roleId);
	
	/**
	 * 更新角色权限
	 * @param roleId
	 * @param ids
	 * @throws ServiceException
	 */
	String modifyRoleAuth(String roleId, String ids) throws ServiceException;
}
