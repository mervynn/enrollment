package com.xinyin.baoming.model.vo;

import java.io.Serializable;

import com.xinyin.baoming.model.pojo.BaseEntity;

/**
 * 
 * 码表
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public class TcCode extends BaseEntity implements Serializable {

	
	public TcCode(){
		
	}
	
	public TcCode(String code, String sort){
		this.code = code;
		this.sort = sort;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6140773217049758273L;
	private String code; // 常量代码
	private String sort; // 排序
	private String name; // 名称
	private String englishName; // 英文名称
	private String type; // 类型
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
