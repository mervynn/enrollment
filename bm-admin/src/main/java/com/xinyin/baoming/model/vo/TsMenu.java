package com.xinyin.baoming.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 菜单表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public class TsMenu extends BaseEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2151866061881739433L;
	private String menuId;
	private String menuName;
	private String menuHref;
	private String parentMenuId;
	private Integer sort;
	private String isMenu;
	private String jspName;
	private String jsSel;
	//ex
	private String username;
	
	public String getJspName() {
		return jspName;
	}

	public String getJsSel() {
		return jsSel;
	}

	public void setJsSel(String jsSel) {
		this.jsSel = jsSel;
	}

	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	private List<TsMenu> menus = new ArrayList<TsMenu>(0);

	// Constructors

	/** default constructor */
	public TsMenu() {
	}

	// Property accessors

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuHref() {
		return this.menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<TsMenu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<TsMenu> menus) {
		this.menus = menus;
	}

	public int compareTo(TsMenu o) {
		if (o.getSort() != null) {
			if (this.getSort() == null) {
				return 1;
			} else if (this.getSort() > o.getSort()) {
				return -1;
			} else {
				return 1;
			}
		}
		return 0;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof TsMenu) { 
			TsMenu m1 = (TsMenu) obj;
			if(m1.getMenuId() != null) {
				return m1.getMenuId().equals(this.menuId);
			}
		}
		return false;
	}
}
