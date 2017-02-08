package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.ThLastClassStudent;

import java.util.List;

/**
 * 
 * ThLastClassStudentMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface ThLastClassStudentMapper{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<ThLastClassStudent> selectBySelective ( ThLastClassStudent record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	ThLastClassStudent selectByPrimaryKey ( String id );

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
	int deleteBySelective ( ThLastClassStudent record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( ThLastClassStudent record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( ThLastClassStudent record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( ThLastClassStudent record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( ThLastClassStudent record );
	
	/**
	 * 删除整表数据
	 * @return
	 */
	int deleteAll();
	
	/**
	 * 班级学生数据复制到历史班级学生表
	 * @return
	 */
	int insertFromClassStudent();
}
