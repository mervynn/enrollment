package com.xinyin.baoming.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TmClass;

/**
 * 
 * 班级表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IClassService{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TmClass> selectBySelective( TmClass record ) throws ServiceException;
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TmClass> selectExactlyBySelective ( TmClass record ) throws ServiceException;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TmClass selectByPrimaryKey( String id ) throws ServiceException;

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String deleteByPrimaryKey( String id ) throws ServiceException;

	/**
	 * 
	 * 删除（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String deleteBySelective ( TmClass record ) throws ServiceException;

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insert( TmClass record ) throws ServiceException;

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insertSelective( TmClass record ) throws ServiceException;

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKeySelective( TmClass record ) throws ServiceException;

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKey( TmClass record ) throws ServiceException;
	
	
	/**
	 * 
	 * wap端查询班级列表
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TmClass> selectBySelectiveForWap( TmClass record ) throws ServiceException;
	
	/**
	 * 
	 * 班级上下架（根据主键ID批量或单条上下架班级）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String upDownClass( String id , String sellFlg, String person) throws ServiceException;
	
	/**
	 * 通过类别id列表查询相关班级信息
	 * @param typeList
	 * @return
	 */
	List<TmClass> selectByClassTypeList(List<String> typeList) throws ServiceException;
	
	
	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	PageList<TmClass> selectOrderDetail(TmClass record) throws ServiceException;
}
