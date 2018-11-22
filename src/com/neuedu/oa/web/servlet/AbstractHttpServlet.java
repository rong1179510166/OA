package com.neuedu.oa.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.RoleEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.service.impl.SystemServiceImpl;

public abstract class AbstractHttpServlet extends HttpServlet {
	public void saveCurrentUser(UserContext uc, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			// 说明之前访问过
			session.invalidate();
		}
		session = req.getSession(true);
		session.setAttribute(UserContext.class.getName(), uc);
	}

	public String getCurrentUserId(HttpServletRequest req) {
		UserContext uc = this.getCurrentUser(req);
		if (uc != null)
			return uc.getId();
		return null;
	}
	public <T> T getBean(Class<T> type) {
		return WebApplicationContextUtils.getWebApplicationContext(this.getServletContext())
		.getBean(type);
	}
	public UserContext getCurrentUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UserContext uc = (UserContext) session.getAttribute(UserContext.class.getName());
		return uc;
	}

	public <T> T request2Ao(HttpServletRequest req, Class<T> type) {
		try {
			// 1创建参数对象
			T ao = type.newInstance();
			// 2设置参数对象中属性的值
			// 2.1找到对象中所有公开set方法
			Method[] ms = type.getMethods();

			Map<String, Method> setterMap = new HashMap<>();
			for (Method m : ms) {
				// 只有1个参数且不是static方法
				if (m.getParameterCount() == 1 && !Modifier.isStatic(m.getModifiers())) {
					String methodName = m.getName();
					if (methodName.startsWith("set")) {
						methodName = lowerFirst(methodName.substring(3));
						setterMap.put(methodName, m);
					}
				}
			}
			// 2.2依次从req中取出对应的参数值(类型匹配)
			for (String fieldName : setterMap.keySet()) {
				// 得到参数值
				String parameterValue = req.getParameter(fieldName);
				// 得到setter方法
				Method setter = setterMap.get(fieldName);
				setter.invoke(ao, parameterValue);
			}
			return ao;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String lowerFirst(String s) {
		char[] cs = s.toCharArray();
		cs[0] += 32;
		return new String(cs);
	}

	private static final String REDIRECT_SUFIX = "redirect:";
	private static final String FORWARD_SUFIX = "forward:";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//把当前用户保存到当前线程中
			UserContext uc=this.getCurrentUser(req);
			UserContext.setCurrent(uc);
			
			Object result = handleRequest(req, resp);
			if (result != null) {
				if (result instanceof String) {
					String url = (String) result;
					if (url.startsWith(REDIRECT_SUFIX)) {
						url = url.substring(REDIRECT_SUFIX.length());
						resp.sendRedirect(req.getContextPath() + url);
					} else if (url.startsWith(FORWARD_SUFIX)) {
						url = url.substring(REDIRECT_SUFIX.length());
						req.getRequestDispatcher(url).forward(req, resp);
					} else {
						req.getRequestDispatcher(url).forward(req, resp);
					}
				}
			}
		} catch (Exception e) {
			resp.sendError(500);
			// 打印异常信息，用于调试
			e.printStackTrace();
		}
	}

	public abstract Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
