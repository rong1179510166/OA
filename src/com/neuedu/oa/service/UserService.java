package com.neuedu.oa.service;

import java.util.List;

import com.neuedu.oa.ao.ImageAo;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.MenuEntity;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.AccountNotExistException;

public interface UserService {

	/**
	 * 用户登录
	 * @param account 账号
	 * @param password 密码
	 * @return 用户上下文
	 * @throws AccountNotExistException
	 * @throws AccountPasswordErrorException
	 * 		   	 
	 */
	public UserContext login(String account,String password,String loginIp)throws Exception;
	
	
	/**
	 * 修改用户头像
	 * @param currentUserId 用户id
	 * @param ao 新的头像
	 */
	void modifyUserAvatar(String currentUserId,ImageAo ao)throws Exception;
	
	ImageAo getUserAvatar(String userId)throws Exception;
	/**
	 * 加载用户拥有的菜单
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	List<MenuEntity> loadUserMenus(String userId)throws Exception;
		
	
}
