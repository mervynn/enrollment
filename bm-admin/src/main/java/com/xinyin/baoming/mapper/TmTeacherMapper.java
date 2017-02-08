package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TmTeacher;

import java.util.List;

/**
 * 
 * TmTeacherMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TmTeacherMapper{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmTeacher> selectBySelective ( TmTeacher record );
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmTeacher> selectExactlyBySelective ( TmTeacher record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TmTeacher selectByPrimaryKey ( String id );

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int deleteByPrimaryKey ( String id );

	/**
	 * 
	 * 删除（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int deleteBySelective ( TmTeacher record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( TmTeacher record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( TmTeacher record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( TmTeacher record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( TmTeacher record );
}
