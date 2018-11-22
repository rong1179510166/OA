package com.neuedu.oa.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.neuedu.oa.Application;
import com.neuedu.oa.dto.UserContext;
@WebListener
public class OnlineUserListener implements HttpSessionAttributeListener,HttpSessionListener{

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name=event.getName();
		if(UserContext.class.getName().equals(name)) {
			//登录个用户
			Application.ONLINE_USER_COUNT++;
		}
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String name=event.getName();
		if(UserContext.class.getName().equals(name)) {
			//用户下线
			Application.ONLINE_USER_COUNT--;
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}

}
