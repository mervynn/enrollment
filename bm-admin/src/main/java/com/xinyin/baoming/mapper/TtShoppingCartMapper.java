package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TmClass;
import com.xinyin.baoming.model.vo.TtShoppingCart;

import java.util.List;

/**
 * 
 * TtShoppingCartMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TtShoppingCartMapper{

	/**
	 * 
	 * 通过学生id查询相关订单信息(商品列表list)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmClass> selectByStudentId ( String studentId );
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	List<TtShoppingCart> selectShoppingCartByStudentId ( String studentId );
	
	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TtShoppingCart> selectBySelective ( TtShoppingCart record );
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TtShoppingCart> selectExactlyBySelective ( TtShoppingCart record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TtShoppingCart selectByPrimaryKey ( String id );

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
	int deleteBySelective ( TtShoppingCart record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( TtShoppingCart record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( TtShoppingCart record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( TtShoppingCart record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( TtShoppingCart record );
	
	/**
	 * 清空购课车数据
	 */
	void deleteAll();
	
	/**
	 * 按班级分组求和，查询所有选课车中的课程数据和未完成订单明细数据
	 * @return
	 */
	List<TtShoppingCart> selectByClassGroup();

}
