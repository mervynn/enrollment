package com.xinyin.baoming.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TsUser;

/**
 * 
 * 系统用户表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface IUserService{

	/**
	 * 根据用户名和密码查询用户(用户登录)
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public TsUser queryUser(String username,String password) throws ServiceException;
	
	/**
	 * 用户信息查询
	 * @param adminUser
	 * @return
	 * @throws ServiceException
	 */
	PageList<TsUser> selectAdminUserList(TsUser adminUser) throws ServiceException;
	
	/**
	 * 通过ID取得用户信息
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	TsUser queryAdminUserInfoById(String id) throws ServiceException;
	
	/**
	 * 用户信息保存
	 * @param adminUser
	 * @return
	 * @throws ServiceException
	 */
	String save(TsUser adminUser)  throws ServiceException;
	
	/**
	 * 用户信息修改
	 * @param adminUser
	 * @return
	 * @throws ServiceException
	 */
	String modify(TsUser adminUser)  throws ServiceException;
	
	/**
	 * 用户信息删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	String deleteAdminUser(String id)  throws ServiceException;
	
	/**
	 * 获取全部系统用户信息
	 * 
	 * @return
	 */
	List<TsUser> selectAllAdminUserList();
}
