package com.neuedu.oa.util;

import com.alibaba.fastjson.JSON;

public class JSONUtil {
	
	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	
}
