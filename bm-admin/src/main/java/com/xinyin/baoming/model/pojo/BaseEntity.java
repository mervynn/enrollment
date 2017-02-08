package com.xinyin.baoming.model.pojo;

import java.util.List;

import com.xinyin.baoming.util.constant.Constant;

/**
 * 
 * 基础POJO
 * 
 * @author HeMingwei
 *
 */
public class BaseEntity implements java.io.Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1131171379496088165L;
	private List<String> idList;
	private String idListStr;
	private String orderSegment;
    private String createDateStr;
    private String updateDateStr;
	//页数
	private int page = Integer.valueOf(Constant.DEFAULT_PAGE);
	//每页数据量
	private int pageSize = Integer.valueOf(Constant.DEFAULT_PAGE_SIZE);
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getSt() {
		return (page - 1) * pageSize;
	}
	public int getEt() {
		return pageSize;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public String getIdListStr() {
		return idListStr;
	}
	public void setIdListStr(String idListStr) {
		this.idListStr = idListStr;
	}
	public int getNow(){
		return Integer.parseInt((System.currentTimeMillis()+"").substring(0,10));
	}
	public String getOrderSegment() {
		return orderSegment;
	}
	public void setOrderSegment(String orderSegment) {
		this.orderSegment = orderSegment;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getUpdateDateStr() {
		return updateDateStr;
	}
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
}
