package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 班级表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmClass extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**班级代码**/
	private String code;

	/**限制最小年龄**/
	private String minAge;

	/**是否只可以旧生报名**/
	private String isOnlyOldStudent;

	/**招生对象**/
	private String targetGroup;

	/**招生人数**/
	private String limitedAmount;

	/**预设名额**/
	private String defaultAmount;

	/**剩余人数**/
	private String remainAmount;

	/**已付款人数**/
	private String paidAmount;

	/**上下架状态**/
	private String status;

	/**学费**/
	private String tuitionFee;

	/**上课时间（星期）**/
	private String classTimeWeek;

	/**商品图片1**/
	private String classTimeBegin;

	/**商品图片1**/
	private String classTimeEnd;

	/**乐器或老师照片**/
	private String picture;

	/**课程类别**/
	private String classTypeId;

	/**授课老师id**/
	private String teacherId;

	/**教室id**/
	private String classroomId;

	/**学期id**/
	private String termId;

	/**乐观锁标识(更新，删除时使用)**/
	private String lock;

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
	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
	public void setMinAge(String minAge){
		this.minAge = minAge;
	}

	public String getMinAge(){
		return this.minAge;
	}
	public void setIsOnlyOldStudent(String isOnlyOldStudent){
		this.isOnlyOldStudent = isOnlyOldStudent;
	}

	public String getIsOnlyOldStudent(){
		return this.isOnlyOldStudent;
	}
	public void setTargetGroup(String targetGroup){
		this.targetGroup = targetGroup;
	}

	public String getTargetGroup(){
		return this.targetGroup;
	}
	public void setLimitedAmount(String limitedAmount){
		this.limitedAmount = limitedAmount;
	}

	public String getLimitedAmount(){
		return this.limitedAmount;
	}
	public void setDefaultAmount(String defaultAmount){
		this.defaultAmount = defaultAmount;
	}

	public String getDefaultAmount(){
		return this.defaultAmount;
	}
	public void setRemainAmount(String remainAmount){
		this.remainAmount = remainAmount;
	}

	public String getRemainAmount(){
		return this.remainAmount;
	}
	public void setPaidAmount(String paidAmount){
		this.paidAmount = paidAmount;
	}

	public String getPaidAmount(){
		return this.paidAmount;
	}
	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}
	public void setTuitionFee(String tuitionFee){
		this.tuitionFee = tuitionFee;
	}

	public String getTuitionFee(){
		return this.tuitionFee;
	}
	public void setClassTimeWeek(String classTimeWeek){
		this.classTimeWeek = classTimeWeek;
	}

	public String getClassTimeWeek(){
		return this.classTimeWeek;
	}
	public void setClassTimeBegin(String classTimeBegin){
		this.classTimeBegin = classTimeBegin;
	}

	public String getClassTimeBegin(){
		return this.classTimeBegin;
	}
	public void setClassTimeEnd(String classTimeEnd){
		this.classTimeEnd = classTimeEnd;
	}

	public String getClassTimeEnd(){
		return this.classTimeEnd;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}

	public String getPicture(){
		return this.picture;
	}
	public void setClassTypeId(String classTypeId){
		this.classTypeId = classTypeId;
	}

	public String getClassTypeId(){
		return this.classTypeId;
	}
	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}

	public String getTeacherId(){
		return this.teacherId;
	}
	public void setClassroomId(String classroomId){
		this.classroomId = classroomId;
	}

	public String getClassroomId(){
		return this.classroomId;
	}
	public void setTermId(String termId){
		this.termId = termId;
	}

	public String getTermId(){
		return this.termId;
	}
	public void setLock(String lock){
		this.lock = lock;
	}

	public String getLock(){
		return this.lock;
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
