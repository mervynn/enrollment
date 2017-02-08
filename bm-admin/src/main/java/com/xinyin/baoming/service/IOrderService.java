package com.xinyin.baoming.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TtOrder;

/**
 * 
 * 订单表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IOrderService{

	/**
	 * 
	 * 查询（匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	PageList<TtOrder> selectBySelective( TtOrder record ) throws ServiceException;
	
	/**
	 * 
	 * 查询（精确匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	List<TtOrder> selectExactlyBySelective( TtOrder record ) throws ServiceException;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	TtOrder selectByPrimaryKey( String id ) throws ServiceException;

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
	String deleteBySelective ( TtOrder record ) throws ServiceException;

	/**
	 * 
	 * 添加
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insert( TtOrder record ) throws ServiceException;

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String insertSelective( TtOrder record ) throws ServiceException;

	/**
	 * 
	 * 修改 （修改有值的字段）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKeySelective( TtOrder record ) throws ServiceException;

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	String updateByPrimaryKey( TtOrder record ) throws ServiceException;
	
	/**
	 * 
	 * 保存订单信息批处理(购物车数据转移到订单表，订单状态为未提交)
	 * 
	 * @author HeMingwei
	 * @param
	 * @return
	 **/
	Map<String, String> saveOrderFromcartBatch(String studentId) throws ServiceException;
	
}
