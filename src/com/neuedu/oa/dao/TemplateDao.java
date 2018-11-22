package com.neuedu.oa.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TemplateDao<T,ID> {
	
	ID insert(T t)throws Exception;
	
	void delete(ID id)throws Exception;
	
	void update(@Param("id") ID id,@Param("map") Map<String,Object> map)throws Exception;
	
	T select(@Param("column")String uniqueColumn,@Param("value")Object value)throws Exception;
	
	boolean exists(@Param("column")String uniqueColumn,@Param("value")Object value)throws Exception;
	
}
