package com.xinyin.baoming.mapper;

import com.xinyin.baoming.model.vo.TsRole;
import com.xinyin.baoming.model.vo.TsRoleAuth;

import java.util.List;

/**
 * 
 * TsRoleMapper数据库操作接口类
 * 
 * 创建时间： 2016-08-07
 * @author HeMingwei
 * @version 1.0
 **/
public interface TsRoleMapper{

List<TsRole> selectAllRoles();
	
    int deleteByPrimaryKey(String roleId);

    int insert(TsRole record);

    int insertSelective(TsRole record);

    TsRole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(TsRole record);

    int updateByPrimaryKey(TsRole record);
    
    List<TsRoleAuth> selectAuthRoleRules(String roleId);
    
    /** 删除角色权限*/
	void deleteRoleAuth(String roleId);
	
	/** 插入角色权限*/
	void insertRoleAuth(TsRoleAuth rule);
}
