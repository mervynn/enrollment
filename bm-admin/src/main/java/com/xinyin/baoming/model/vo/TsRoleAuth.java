package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 角色用户权限关联表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TsRoleAuth extends BaseEntity implements Serializable {

	/**角色id**/
	private String roleId;

	/**菜单ID**/
	private String menuId;

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getRoleId(){
		return this.roleId;
	}
	public void setMenuId(String menuId){
		this.menuId = menuId;
	}

	public String getMenuId(){
		return this.menuId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof String) {
			String menuId = (String) obj;
			return this.menuId.equals(menuId);	
		}
		return false;
	}
}
