package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 学期信息
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmTerm extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**学期名称**/
	private String name;

	/**学期开始时间**/
	private String beginDate;

	/**学期结束时间**/
	private String endDate;

	/**学期状态**/
	private String status;

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
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	public void setBeginDate(String beginDate){
		this.beginDate = beginDate;
	}

	public String getBeginDate(){
		return this.beginDate;
	}
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return this.endDate;
	}
	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
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
