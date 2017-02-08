package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TcCode;

import java.util.List;

/**
 * 
 * TcCodeMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TcCodeMapper{

	/**
	 * 码表全部词典信息查询
	 * 
	 * @return
	 */
    public List<TcCode> selectSysCode();
    
    /**
     * 码表中新闻关键词列表查询
     * 
     * @return
     */
    public List<TcCode> selectTcCodeForNewsKeyWords();
    
    int deleteByPrimaryKey(TcCode key);

    int insert(TcCode record);

    int insertSelective(TcCode record);

    TcCode selectByPrimaryKey(TcCode key);

    int updateByPrimaryKeySelective(TcCode record);

    int updateByPrimaryKey(TcCode record);
}
