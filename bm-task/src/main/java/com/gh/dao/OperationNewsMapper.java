package com.gh.dao;

import java.util.List;

import com.gh.model.vo.OperationNews;

public interface OperationNewsMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(OperationNews record);

    int insertSelective(OperationNews record);

    OperationNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperationNews record);

    int updateByPrimaryKey(OperationNews record);
    
    /**
     * 删除{days}天前的历史新闻
     * 
     * @param days
     * @param isRecommend
     * @param persistNesAmount
     * @return
     */
    int deleteHistoryNews(Integer days, String persistNesAmount);

    /**
     * 查询历史新闻
     * 
     * @param record
     * @return
     */
    List<OperationNews> selectNewsList(OperationNews record);
}