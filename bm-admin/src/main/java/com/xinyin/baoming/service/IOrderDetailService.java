package com.xinyin.baoming.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TtOrderDetail;

/**
 * 
 * 订单明细表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IOrderDetailService{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TtOrderDetail> selectBySelective( TtOrderDetail record ) throws ServiceException;
	
	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TtOrderDetail> select( TtOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TtOrderDetail selectByPrimaryKey( String id ) throws ServiceException;

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
	String deleteBySelective ( TtOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insert( TtOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insertSelective( TtOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKeySelective( TtOrderDetail record ) throws ServiceException;

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKey( TtOrderDetail record ) throws ServiceException;
}
