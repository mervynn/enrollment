package com.xinyin.baoming.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.mapper.TsMenuMapper;
import com.xinyin.baoming.model.vo.TsMenu;
import com.xinyin.baoming.service.IMenuService;
import com.xinyin.baoming.util.Sequence;

/**
 * 
 * 菜单表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@Service
public class MenuServiceImpl extends BaseService implements IMenuService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5143920356880710501L;
	@Autowired
	private TsMenuMapper menuMapper;
	
	public List<TsMenu> getRootMenus() throws ServiceException {
		try {
			return menuMapper.selectMenuRoot();
		} catch (Exception e) {
			throw new ServiceException("获得根菜单", e);
		}
	}
	public void addMenu(TsMenu menu,String parentMenuId) throws ServiceException {
		try {
			if(parentMenuId != null)  {
				menu.setParentMenuId(parentMenuId);
			}
			if(menu.getMenuId() != null && !menu.getMenuId().isEmpty())  {
				TsMenu selMenu = menuMapper.selectMenuById(menu.getMenuId());
				menu.setParentMenuId(selMenu.getParentMenuId());
			}
			menu.setMenuId(Sequence.nextId());
			menuMapper.insertMenu(menu);
		} catch (Exception e) {
			throw new ServiceException("新增菜单", e);
		}
	}
	public void removeMenu(TsMenu menu) throws ServiceException {
		try {
			menuMapper.deleteMenu(menu);
		} catch (Exception e) {
			throw new ServiceException("删除菜单", e);
		}
	}
	public TsMenu getMenuById(String menuId, boolean flag) throws ServiceException {
		try {
			return menuMapper.selectMenuById(menuId);
		} catch (Exception e) {
			throw new ServiceException("根据菜单ID 获得菜单", e);
		}
	}
	public void modifyMenu(TsMenu menu) throws ServiceException {
		try {
			menuMapper.updateMenu(menu);
		} catch (Exception e) {
			throw new ServiceException("修改菜单", e);
		}
	}
	@Override
	public List<TsMenu> getRootMenusByUserName(String username) throws ServiceException {
		try {
			List<TsMenu> allMenus = menuMapper.selectUserHasMenu(username);
			List<TsMenu> rootMenus = new ArrayList<TsMenu>();
			for (int i = 0; i < allMenus.size(); i++) {
				TsMenu menu = allMenus.get(i);
//				if(menu.getParentMenuId() == null) {
					//查找最根目录菜单
					rootMenus.add(menu);
//				}
			}
			for (int i = 0; i < rootMenus.size(); i++) {
				TsMenu rootMenu = rootMenus.get(i);
				//递归查找子菜单
				List<TsMenu> childs = findChildsMenu(rootMenu.getMenuId(), allMenus);
				rootMenu.setMenus(childs);
				if(childs.size()!=0 || StringUtils.isBlank(rootMenu.getParentMenuId())){
					rootMenu.setMenus(childs);
				}else{
					rootMenus.remove(i);
					i--;
				}
				
			}
			return rootMenus;
		} catch (Exception e) {
			throw new ServiceException("查询用户拥有的菜单权限", e);
		}
	}
	private List<TsMenu> findChildsMenu(String menuId, List<TsMenu> allMenus) {
		if(menuId == null) { 
			return null;
		}
		List<TsMenu> childs = new ArrayList<TsMenu>();
		for (int i = 0; i < allMenus.size(); i++) {
			TsMenu menu = allMenus.get(i);
			if(!"1".equals(menu.getIsMenu()))continue;
			if(menuId.equals(menu.getParentMenuId())) {
				//循环所有菜单。如果找到子菜单。则加入到子菜单集中
				childs.add(menu);
				//并且递归查找所有的子菜单
				menu.setMenus(findChildsMenu(menu.getMenuId(), allMenus));
			}
		}
		return childs;
	}
}
