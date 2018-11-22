package com.neuedu.oa.dao;

import java.util.List;

import com.neuedu.oa.entity.RoleEntity;

public interface RoleDao extends TemplateDao<RoleEntity, String>{

	List<RoleEntity> loadAll(String currentUserId)throws Exception;
	
	
}
