package com.xinyin.baoming.service;

import java.util.List;

import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TsMenu;

/**
 * 
 * 菜单表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IMenuService{

	/**
	 * 获得根 菜单
	 * @return
	 * @throws ServiceException 
	 */
	List<TsMenu> getRootMenus() throws ServiceException;
	/**
	 * 根据菜单ID 获得菜单
	 * @param menuId
	 * @param flag 
	 */
	TsMenu getMenuById(String menuId, boolean flag) throws ServiceException;
	/**
	 * 新增菜单
	 * @param menu
	 * @param parentId
	 */
	void addMenu(TsMenu menu, String parentId) throws ServiceException;
	/**
	 * 修改菜单
	 * @param menu
	 */
	void modifyMenu(TsMenu menu) throws ServiceException;
	/**
	 * 删除菜单
	 * @param menu
	 */
	void removeMenu(TsMenu menu) throws ServiceException;
	/**
	 * 查询用户拥有的菜单权限
	 * @param loginUser
	 * @return
	 * @throws ServiceException
	 */
	List<TsMenu> getRootMenusByUserName(String username) throws ServiceException;
}
