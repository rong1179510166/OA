package com.neuedu.oa.dao;

import java.util.List;

import com.neuedu.oa.entity.MenuEntity;
import com.neuedu.oa.entity.RoleEntity;

public interface MenuDao extends TemplateDao<MenuEntity, String>{

	List<MenuEntity> selectByUserId(String userId)throws Exception;

	
	
}
