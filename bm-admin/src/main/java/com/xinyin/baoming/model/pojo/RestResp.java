package com.xinyin.baoming.model.pojo;

/**
 * Rest接口结果POJO
 * 
 * @author HeMingwei
 *
 */
public class RestResp {
	private String code;
	private String msg;
	private Object data;
	private String maxPage;
	private String page;
	private String totalCount;
	
	private String succcb;
	private boolean needauth = true; // 图片验证码开关
	
	public String getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getSucccb() {
		return succcb;
	}
	public void setSucccb(String succcb) {
		this.succcb = succcb;
	}
	public boolean isNeedauth() {
		return needauth;
	}
	public void setNeedauth(boolean needauth) {
		this.needauth = needauth;
	}
	
}
