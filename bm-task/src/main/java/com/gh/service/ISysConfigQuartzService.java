package com.gh.service;

import com.gh.exception.ServiceException;
import com.gh.model.vo.SysConfigQuartz;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 计划任务管理服务
 * 
 * @author HeMingwei
 *
 */
public interface ISysConfigQuartzService {
	
	/**
	 * 计划任务查询
	 * @param sysConfigQuartz
	 * @return
	 * @throws ServiceException
	 */
	PageList<SysConfigQuartz> selectConfigQuartzList(SysConfigQuartz sysConfigQuartz) throws ServiceException;
	
	/**
	 * 通过ID取得计划任务
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	SysConfigQuartz queryConfigQuartzInfoById(String id) throws ServiceException;
	
	/**
	 * 计划任务保存
	 * @param sysConfigQuartz
	 * @return
	 * @throws ServiceException
	 */
	String save(SysConfigQuartz sysConfigQuartz)  throws ServiceException;
	
	/**
	 * 计划任务修改
	 * @param sysConfigQuartz
	 * @return
	 * @throws ServiceException
	 */
	String modify(SysConfigQuartz sysConfigQuartz)  throws ServiceException;
	
	/**
	 * 计划任务删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	String deleteConfigQuartz(String id)  throws ServiceException;
}
