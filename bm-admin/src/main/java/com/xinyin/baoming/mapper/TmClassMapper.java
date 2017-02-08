package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TmClass;

import java.util.List;

/**
 * 
 * TmClassMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TmClassMapper{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmClass> selectBySelective ( TmClass record );
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmClass> selectExactlyBySelective ( TmClass record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TmClass selectByPrimaryKey ( String id );

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
	int deleteBySelective ( TmClass record );

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insert( TmClass record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int insertSelective( TmClass record );

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKeySelective( TmClass record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int updateByPrimaryKey ( TmClass record );
	
	/**
	 * 
	 * 商品加锁
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	int lockData ( String id );
	
	/**
	 * 删除整表数据
	 * @return
	 */
	int deleteAll();
	
	/**
	 * 通过类别id列表查询相关班级信息
	 * @param typeList
	 * @return
	 */
	List<TmClass> selectByClassTypeList(List<String> list);
	
	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	List<TmClass> selectOrderDetail(String id);
	
	/**
	 * 通过学生id查询已提交和已付款的订单明细数据
	 * @param id
	 * @return
	 */
	List<TmClass> selectOrderDetailByStudentId(String id);
	
}
