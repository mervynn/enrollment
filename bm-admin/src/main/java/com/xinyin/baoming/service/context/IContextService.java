package com.xinyin.baoming.service.context;

import java.util.List;

import com.xinyin.baoming.model.vo.TcCode;
import com.xinyin.baoming.model.vo.TmClassType;
import com.xinyin.baoming.model.vo.TmSchool;

public interface IContextService {
	
	/**
	 * 系统初始化
	 */
	public void init();

	/**
	 * 初始化码表信息到内存
	 */
	public void initDic();
	
	/**
	 * 初始化课程类型信息到内存
	 */
	public void initClassType();
	
	/**
	 * 初始化学校信息到内存
	 */
	public void initSchool();
	
	/**
	 * 初始化配置文件信息到内存
	 */
	public void initProperties();
	
	/**
	 * 通过词典类型从内存中取得词典信息
	 * @param code 词典类型
	 * @return
	 */
	public List<TcCode> getCodeList(String type);
	
	/**
	 * 通过父id或是否跟目录取得课程类型数据
	 * 
	 * @param parentId 父id
	 * @return
	 */
	public List<TmClassType> getClassTypeList(String parentId);
	
	/**
	 * 通过id取得课程类型
	 * 
	 * @param parentId 父id
	 * @return
	 */
	public TmClassType getClassTypeById(String classTypeId);
	
	/**
	 * wap端左侧菜单树
	 * 
	 * @param parentId 父id
	 * @return
	 */
	public List<TmClassType> getLeftTree();
	
	/**
	 * 取得全部学校信息
	 * 
	 * @param parentId 父id
	 * @return
	 */
	public List<TmSchool> getAllSchool();
}
