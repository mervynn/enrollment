package com.gh.dao;

import java.util.List;

import com.gh.model.vo.OperationNewsWebsites;

public interface OperationNewsWebsitesMapper {
	
	List<OperationNewsWebsites> selectNewsWebsitesList(OperationNewsWebsites record);
	
    int deleteByPrimaryKey(String id);

    int insert(OperationNewsWebsites record);

    int insertSelective(OperationNewsWebsites record);

    OperationNewsWebsites selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OperationNewsWebsites record);

    int updateByPrimaryKey(OperationNewsWebsites record);
}