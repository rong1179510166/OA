package com.neuedu.oa;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	static final private Logger LOG=LoggerFactory.getLogger(Application.class);
	final private static Properties CONFIG=new Properties();
	public static int ONLINE_USER_COUNT=0;
	static {
		
		init();
	}
	private static void init() {
		try {
			LOG.debug("从[/config.properties]加载配置文件");
			CONFIG.load(Application.class.getResourceAsStream("/config.properties"));
		}catch(Exception e) {
			e.printStackTrace();
			LOG.error("加载配置文件失败",e);
		}
	}
	
	public static String getString(String key,String defaultValue) {
		return CONFIG.getProperty(key,defaultValue);
	}
	
	public static int getInt(String key,int defaultValue) {
		String value=CONFIG.getProperty(key);
		if(value==null) {
			return defaultValue;
		}
		value=value.trim();
		if(value.length()==0) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}
	
	
	
}
