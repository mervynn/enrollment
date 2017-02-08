package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 班级类型
 * 
 * 创建时间： 2016-08-15
 * @author HeMingwei
 * @version 1.0
 **/
@SuppressWarnings("serial")
public class TmClassType extends BaseEntity implements Serializable {

	/**流水id**/
	private String id;

	/**类型名称**/
	private String name;

	/**图片url**/
	private String pictureUrl;

	/**父级id**/
	private String parentId;

	/**排序**/
	private String sort;

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
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl(){
		return this.pictureUrl;
	}
	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}
	public void setSort(String sort){
		this.sort = sort;
	}

	public String getSort(){
		return this.sort;
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
