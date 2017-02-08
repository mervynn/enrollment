package com.xinyin.baoming.mapper;

import java.sql.SQLException;
import java.util.List;

import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.model.vo.TsUser;

/**
 * 
 * TsMenuMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TsMenuMapper{

	/**
	 * 获得根菜单
	 * @return
	 * @throws SQLException 
	 */
	List<TsMenu> selectMenuRoot();
	/**
	 * 插入菜单
	 * @param menu
	 */
	void insertMenu(TsMenu menu);
	/**
	 * 更新菜单
	 * @param menu
	 */
	void updateMenu(TsMenu menu);
	/**
	 * 删除菜单
	 * @param menu
	 * @throws SQLException 
	 */
	void deleteMenu(TsMenu menu);
	/**
	 * 根据ID 获得菜单
	 * @param menuId
	 * @param flag 
	 * @return
	 * @throws SQLException 
	 */
	TsMenu selectMenuById(String menuId);
	/**
	 * 查询用户有权操作的菜单
	 * @param loginUser
	 * @return
	 * @throws SQLException
	 */
	List<TsMenu> selectUserHasMenu(String username);
	/**
	 * 查询用户没有操作权限的JSP页面操作
	 * @param user
	 * @throws SQLException
	 */
	List<TsMenu> selectDisableJspOpt(TsUser user);
}
