package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TmContent;

/**
 * 
 * TmContentMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-19
 * @author HeMingwei
 * @version 1.0
 **/
public interface TmContentMapper{

    int deleteByPrimaryKey(String id);

    int insert(TmContent record);

    int insertSelective(TmContent record);

    TmContent selectByPrimaryKey(String id);
    
    TmContent selectByType(String type);
    
    int updateByPrimaryKeySelective(TmContent record);

    int updateByPrimaryKey(TmContent record);
}
