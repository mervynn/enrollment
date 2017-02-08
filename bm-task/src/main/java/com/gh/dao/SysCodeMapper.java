package com.gh.dao;

import java.util.List;

import com.gh.model.vo.SysCode;

public interface SysCodeMapper {
	
	/**
	 * 码表全部词典信息查询
	 * 
	 * @return
	 */
    public List<SysCode> selectSysCode();
    
    /**
     * 码表中新闻关键词列表查询
     * 
     * @return
     */
    public List<SysCode> selectSysCodeForNewsKeyWords();
    
    int deleteByPrimaryKey(SysCode key);

    int insert(SysCode record);

    int insertSelective(SysCode record);

    SysCode selectByPrimaryKey(SysCode key);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);
}