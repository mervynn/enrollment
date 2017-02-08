package com.xinyin.baoming.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TcCode;

/**
 * 
 * 码表信息管理服务层
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface ICodeService{

	/**
	 * 系统常量查询
	 * @param sysCode
	 * @return
	 * @throws ServiceException
	 */
	PageList<TcCode> selectSysCodeList(TcCode sysCode) throws ServiceException;
	
	/**
	 * 通过ID取得系统常量
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	TcCode querySysCodeInfoById(String code, String sort) throws ServiceException;
	
	/**
	 * 系统常量保存
	 * @param sysCode
	 * @return
	 * @throws ServiceException
	 */
	String save(TcCode sysCode)  throws ServiceException;
	
	/**
	 * 系统常量修改
	 * @param sysCode
	 * @return
	 * @throws ServiceException
	 */
	String modify(TcCode sysCode)  throws ServiceException;
	
	/**
	 * 系统常量删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	String deleteSysCode(String id)  throws ServiceException;
}
