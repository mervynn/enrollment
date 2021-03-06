package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 旧生课程推荐表
 * 
 * 创建时间： 2016-08-14
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TtRecommend extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**学生id**/
	private String studentId;

	/**班级id**/
	private String classId;

	/**被推荐老师id**/
	private String teacherId;

	/**是否通过审批**/
	private String isThroughApproval;

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
	public void setStudentId(String studentId){
		this.studentId = studentId;
	}

	public String getStudentId(){
		return this.studentId;
	}
	public void setClassId(String classId){
		this.classId = classId;
	}

	public String getClassId(){
		return this.classId;
	}
	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}

	public String getTeacherId(){
		return this.teacherId;
	}
	public void setIsThroughApproval(String isThroughApproval){
		this.isThroughApproval = isThroughApproval;
	}

	public String getIsThroughApproval(){
		return this.isThroughApproval;
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
