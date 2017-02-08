package com.gh.dao;

import com.gh.model.vo.SysConfigQuartz;

public interface SysConfigQuartzMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(SysConfigQuartz record);

    int insertSelective(SysConfigQuartz record);

    SysConfigQuartz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysConfigQuartz record);

    int updateByPrimaryKey(SysConfigQuartz record);
}