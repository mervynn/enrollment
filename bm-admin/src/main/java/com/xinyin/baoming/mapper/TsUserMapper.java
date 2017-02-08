package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TsUser;

import java.util.List;

/**
 * 
 * TsUserMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TsUserMapper{

	List<TsUser> selectAllAdminUserList();
	
    int deleteByPrimaryKey(String username);

    int insert(TsUser record);

    int insertSelective(TsUser record);

    TsUser selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(TsUser record);

    int updateByPrimaryKey(TsUser record);
    
    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    TsUser queryByUnameAndPword(String username,String password);
    
    List<TsUser> selectAdminUserList(TsUser record);
}
