package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TmStudent;

import java.util.List;

/**
 * 
 * TmStudentMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-13
 * @author HeMingwei
 * @version 1.0
 **/
public interface TmStudentMapper{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmStudent> selectBySelective ( TmStudent record );
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmStudent> selectExactlyBySelective ( TmStudent record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TmStudent selectByPrimaryKey ( String id );

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
	int deleteBySelective ( TmStudent record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( TmStudent record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( TmStudent record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( TmStudent record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( TmStudent record );
}
