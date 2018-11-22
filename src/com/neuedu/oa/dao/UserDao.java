package com.neuedu.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.neuedu.oa.entity.UserEntity;

public interface UserDao extends TemplateDao<UserEntity, String> {
	
	/**
	 * 模糊分页查询
	 * 
	 * @param rows     当前页的行
	 * @param key      查询关键字, 如果为null，则查询全部
	 * @param state    查询用户状态 0 1 或null ，如果为null，则查询全部
	 * @param pageNo   查询页
	 * @param pageSize 每页大小
	 * @return 总记录数
	 * @throws Exception
	 */
	List<UserEntity> selectByLike(@Param("key")String key, @Param("state")Integer state,@Param("currentUserId") String currentUserId)
			throws Exception;


}