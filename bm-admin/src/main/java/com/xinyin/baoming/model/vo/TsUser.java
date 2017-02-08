package com.xinyin.baoming.model.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 系统用户表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TsUser extends BaseEntity implements Serializable {

	/**管理员用户名**/
	private String username;

	/**管理员密码**/
	private String password;

	/**真实姓名**/
	private String realName;

	/**联系手机**/
	private String phone;

	/**上级用户**/
	private String upUser;

	/**角色ID**/
	private String roleId;

	/**录入人**/
	private String createBy;

	/**录入时间**/
	private java.util.Date createDate;

	/**变更人**/
	private String updateBy;

	/**变更时间**/
	private java.util.Date updateDate;
	
	private String parentMenuId;
	//ex
	//角色拥有的所有菜单
	private String roleName;
	private List<TsMenu> hasMenu;
	private Map<String, List<TsMenu>> disableJspOptMap;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}
	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}
	public void setUpUser(String upUser){
		this.upUser = upUser;
	}

	public String getUpUser(){
		return this.upUser;
	}
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getRoleId(){
		return this.roleId;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	public String getCreateBy(){
		return this.createBy;
	}
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate(){
		return this.createDate;
	}
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	public String getUpdateBy(){
		return this.updateBy;
	}
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<TsMenu> getHasMenu() {
		return hasMenu;
	}

	public void setHasMenu(List<TsMenu> hasMenu) {
		this.hasMenu = hasMenu;
	}

	public Map<String, List<TsMenu>> getDisableJspOptMap() {
		return disableJspOptMap;
	}

	public void setDisableJspOptMap(Map<String, List<TsMenu>> disableJspOptMap) {
		this.disableJspOptMap = disableJspOptMap;
	}

}
