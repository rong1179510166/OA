package com.neuedu.oa.service;


import java.util.List;

import com.neuedu.oa.ao.QueryUserAo;
import com.neuedu.oa.ao.UserAo;
import com.neuedu.oa.dto.QueryResult;
import com.neuedu.oa.entity.RoleEntity;
import com.neuedu.oa.entity.UserEntity;

public interface SystemService {

	UserEntity addUser(UserAo ao)throws Exception;
	
	void deleteUser(String id)throws Exception;
	
	UserEntity modifyUser(String id,UserAo ao)throws Exception;
	
	UserEntity loadUser(String id)throws Exception;
	
	QueryResult<UserEntity> queryUser(QueryUserAo ao)throws Exception;
	
	List<RoleEntity> queryRole()throws Exception;
	
	
	
}
