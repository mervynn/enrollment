package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 授课老师信息
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmTeacher extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**身份证编号**/
	private String idcardNo;

	/**手机号**/
	private String phonenumber;

	/**姓名**/
	private String name;

	/**性别**/
	private String sex;

	/**头像**/
	private String pictureUrl;

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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}
	public void setIdcardNo(String idcardNo){
		this.idcardNo = idcardNo;
	}

	public String getIdcardNo(){
		return this.idcardNo;
	}
	public void setPhonenumber(String phonenumber){
		this.phonenumber = phonenumber;
	}

	public String getPhonenumber(){
		return this.phonenumber;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return this.sex;
	}
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl(){
		return this.pictureUrl;
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
}
