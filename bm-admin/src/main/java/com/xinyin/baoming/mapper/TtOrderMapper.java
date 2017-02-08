package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TtOrder;

import java.util.List;

/**
 * 
 * TtOrderMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-15
 * @author HeMingwei
 * @version 1.0
 **/
public interface TtOrderMapper{

	/**
	 * 
	 * 查询（模糊匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TtOrder> selectBySelective ( TtOrder record );

	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TtOrder> selectExactlyBySelective ( TtOrder record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TtOrder selectByPrimaryKey ( String id );

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
	int deleteBySelective ( TtOrder record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( TtOrder record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( TtOrder record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( TtOrder record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( TtOrder record );
	
	/**
	 * 清空表数据
	 */
	void deleteAll();
	
	/**
	 * 根据学生、班级查询尚未支付或已支付的订单
	 * 
	 * @param studentId
	 * @param classId
	 * @return
	 */
	List<TtOrder> selectByStudentAndClass(String studentId, String classId);
}
