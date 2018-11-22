package com.neuedu.oa.web.servlet;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.util.JSONUtil;

public abstract class AbstractAjaxHttpServlet extends AbstractHttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//把当前用户保存到当前线程中
		UserContext uc=this.getCurrentUser(req);
		UserContext.setCurrent(uc);
		JSONResponse result = new JSONResponse();
		try {
			// 1处理请求
			Object data = handleRequest(req, resp);
			result.setCode("ok");
			result.setData(data);
		} catch (OAException e) {
			result.setCode("error");
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode("error");
			result.setMessage("系统繁忙，请稍候再试!");
			// 打印异常信息，用于调试
			e.printStackTrace();
		} finally {
			Writer out = resp.getWriter();
			out.write(JSONUtil.toJsonString(result));
			out.close();
		}
	}
	
	
}
