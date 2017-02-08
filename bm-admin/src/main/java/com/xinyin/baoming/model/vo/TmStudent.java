package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 学生资料表
 * 
 * 创建时间： 2016-08-13
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmStudent extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**证件类型**/
	private String cardType;

	/**身份证号码**/
	private String idcardNo;

	/**姓名**/
	private String name;

	/**性别**/
	private String sex;

	/**是否石狮本地**/
	private String isLocalOrNot;

	/**家长姓名**/
	private String parentName;
	
	/**家长家长手机号码**/
	private String parentPhonenumber;

	/**所在学校id**/
	private String schoolId;

	/**就读年级**/
	private String grade;

	/**头像**/
	private String pictureUrl;

	/**出生日期**/
	private String birthDate;

	/**家庭地址**/
	private String address;

	/**学籍号**/
	private String studentCode;

	/**是否是低保户**/
	private String isLowincome;

	/**家庭电话**/
	private String homePhone;

	/**账号id**/
	private String accountId;

	/**是否黑名单**/
	private String isBlacklist;

	/**黑名单原因**/
	private String blacklistReason;

	/**黑名单原因图片**/
	private String blacklistReasonUrl;

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
	public void setCardType(String cardType){
		this.cardType = cardType;
	}

	public String getCardType(){
		return this.cardType;
	}
	public void setIdcardNo(String idcardNo){
		this.idcardNo = idcardNo;
	}

	public String getIdcardNo(){
		return this.idcardNo;
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
	public void setIsLocalOrNot(String isLocalOrNot){
		this.isLocalOrNot = isLocalOrNot;
	}

	public String getIsLocalOrNot(){
		return this.isLocalOrNot;
	}
	public void setParentName(String parentName){
		this.parentName = parentName;
	}

	public String getParentName(){
		return this.parentName;
	}
	public void setSchoolId(String schoolId){
		this.schoolId = schoolId;
	}

	public String getSchoolId(){
		return this.schoolId;
	}
	public void setGrade(String grade){
		this.grade = grade;
	}

	public String getGrade(){
		return this.grade;
	}
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl(){
		return this.pictureUrl;
	}
	public void setBirthDate(String birthDate){
		this.birthDate = birthDate;
	}

	public String getBirthDate(){
		return this.birthDate;
	}
	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}
	public void setStudentCode(String studentCode){
		this.studentCode = studentCode;
	}

	public String getStudentCode(){
		return this.studentCode;
	}
	public void setIsLowincome(String isLowincome){
		this.isLowincome = isLowincome;
	}

	public String getIsLowincome(){
		return this.isLowincome;
	}
	public void setHomePhone(String homePhone){
		this.homePhone = homePhone;
	}

	public String getHomePhone(){
		return this.homePhone;
	}
	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getAccountId(){
		return this.accountId;
	}
	public void setIsBlacklist(String isBlacklist){
		this.isBlacklist = isBlacklist;
	}

	public String getIsBlacklist(){
		return this.isBlacklist;
	}
	public void setBlacklistReason(String blacklistReason){
		this.blacklistReason = blacklistReason;
	}

	public String getBlacklistReason(){
		return this.blacklistReason;
	}
	public void setBlacklistReasonUrl(String blacklistReasonUrl){
		this.blacklistReasonUrl = blacklistReasonUrl;
	}

	public String getBlacklistReasonUrl(){
		return this.blacklistReasonUrl;
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

	public String getParentPhonenumber() {
		return parentPhonenumber;
	}

	public void setParentPhonenumber(String parentPhonenumber) {
		this.parentPhonenumber = parentPhonenumber;
	}
	
}
