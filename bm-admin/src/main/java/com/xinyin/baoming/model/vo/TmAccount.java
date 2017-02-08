package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 学生账号表
 * 
 * 创建时间： 2016-08-13
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmAccount extends BaseEntity implements Serializable {

	/**流水di**/
	private String id;

	/**手机号**/
	private String phonenumber;

	/**密码**/
	private String password;

	/**默认学生id**/
	private String defultStudentId;

	/**录入人**/
	private String createBy;

	/**录入时间**/
	private java.util.Date createDate;

	/**变更人**/
	private String updateBy;

	/**变更时间**/
	private java.util.Date updateDate;

	/**备注**/
	private String remarks;
	
	/** session中存储分类id **/
	private String classTypeId;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}
	public void setPhonenumber(String phonenumber){
		this.phonenumber = phonenumber;
	}

	public String getPhonenumber(){
		return this.phonenumber;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}
	public void setDefultStudentId(String defultStudentId){
		this.defultStudentId = defultStudentId;
	}

	public String getDefultStudentId(){
		return this.defultStudentId;
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
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	public String getRemarks(){
		return this.remarks;
	}

	public String getClassTypeId() {
		return classTypeId;
	}

	public void setClassTypeId(String classTypeId) {
		this.classTypeId = classTypeId;
	}
	
}
