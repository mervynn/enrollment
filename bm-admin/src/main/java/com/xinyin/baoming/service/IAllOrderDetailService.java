package com.xinyin.baoming.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TzAllOrderDetail;

/**
 * 
 * 订单明细归档表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IAllOrderDetailService{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TzAllOrderDetail> selectBySelective( TzAllOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TzAllOrderDetail selectByPrimaryKey( String id ) throws ServiceException;

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
	String deleteBySelective ( TzAllOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insert( TzAllOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insertSelective( TzAllOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKeySelective( TzAllOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKey( TzAllOrderDetail record ) throws ServiceException;
}
