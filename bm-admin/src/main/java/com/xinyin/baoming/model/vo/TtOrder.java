package com.xinyin.baoming.model.vo;

import java.io.Serializable;
import java.util.List;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 订单表
 * 
 * 创建时间： 2016-08-14
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TtOrder extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**学生id**/
	private String studentId;

	/**订单总金额**/
	private String totalMoney;

	/**订单状态**/
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
	
	/** 家长手机号码 **/
	private String phonenumber;
	
	/** 学生证件号码 **/
	private String idcardNo;
	
	/** 关联的订单明细 **/
	private List<TmClass> orderDetail;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}
	public void setStudentId(String studentId){
		this.studentId = studentId;
	}

	public String getStudentId(){
		return this.studentId;
	}
	public void setTotalMoney(String totalMoney){
		this.totalMoney = totalMoney;
	}

	public String getTotalMoney(){
		return this.totalMoney;
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

	public List<TmClass> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<TmClass> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}
	
}
