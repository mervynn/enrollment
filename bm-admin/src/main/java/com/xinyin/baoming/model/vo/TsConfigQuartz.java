package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 计划任务表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TsConfigQuartz extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**任务名称**/
	private String triggerName;

	/**触发时间(CRON表达式)**/
	private String cronExpression;

	/**任务详细说明**/
	private String jobDetailName;

	/**任务java对象**/
	private String targetObject;

	/**方法名称**/
	private String methodName;

	/**并发**/
	private String concurrent;

	/**任务状态**/
	private String state;

	/**是spring组件**/
	private String isSpringBean;

	/**创建者**/
	private String createBy;

	/**创建时间**/
	private java.util.Date createDate;

	/**更新者**/
	private String updateBy;

	/**更新时间**/
	private java.util.Date updateDate;

	/**备注信息**/
	private String remarks;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}
	public void setTriggerName(String triggerName){
		this.triggerName = triggerName;
	}

	public String getTriggerName(){
		return this.triggerName;
	}
	public void setCronExpression(String cronExpression){
		this.cronExpression = cronExpression;
	}

	public String getCronExpression(){
		return this.cronExpression;
	}
	public void setJobDetailName(String jobDetailName){
		this.jobDetailName = jobDetailName;
	}

	public String getJobDetailName(){
		return this.jobDetailName;
	}
	public void setTargetObject(String targetObject){
		this.targetObject = targetObject;
	}

	public String getTargetObject(){
		return this.targetObject;
	}
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}

	public String getMethodName(){
		return this.methodName;
	}
	public void setConcurrent(String concurrent){
		this.concurrent = concurrent;
	}

	public String getConcurrent(){
		return this.concurrent;
	}
	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}
	public void setIsSpringBean(String isSpringBean){
		this.isSpringBean = isSpringBean;
	}

	public String getIsSpringBean(){
		return this.isSpringBean;
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
