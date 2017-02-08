package com.xinyin.baoming.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xinyin.baoming.exception.ServiceException;
import com.xinyin.baoming.model.vo.TmContent;

/**
 * 
 * h5内容表信息管理服务层
 * 
 * 创建时间： 2016-08-19
 * @author HeMingwei
 * @version 1.0
 **/
public interface IContentService{

	/**
	 * 内容查询
	 * @param contentInfo
	 * @return
	 * @throws ServiceException
	 */
	PageList<TmContent> selectContentList(TmContent contentInfo) throws ServiceException;
	
	/**
	 * 通过ID取得内容
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	TmContent queryContentInfoById(String id) throws ServiceException;
	
	/**
	 * 通过内容类型取得内容
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	TmContent selectByType(String name) throws ServiceException;
	
	/**
	 * 内容保存
	 * @param contentInfo
	 * @return
	 * @throws ServiceException
	 */
	String save(TmContent contentInfo)  throws ServiceException;
	
	/**
	 * 内容修改
	 * @param contentInfo
	 * @return
	 * @throws ServiceException
	 */
	String modify(TmContent contentInfo)  throws ServiceException;
	
	/**
	 * 内容删除
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	String deleteContent(String id)  throws ServiceException;
}
