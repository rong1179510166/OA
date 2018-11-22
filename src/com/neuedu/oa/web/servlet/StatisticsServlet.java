package com.neuedu.oa.web.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.oa.Application;
import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.UserService;
import com.neuedu.oa.service.impl.UserServiceImpl;
import com.neuedu.oa.util.JSONUtil;

@WebServlet("/api/system/statistics")
public class StatisticsServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONResponse result=new JSONResponse();
		try {
			// 4 返回结果
			result.setCode("ok");
			result.setData(Application.ONLINE_USER_COUNT);

		} catch (OAException e) {
			result.setCode("0001");
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode("error");
			result.setMessage("系统繁忙，请稍候再试!");
			//打印异常信息，用于调试
			e.printStackTrace();
		} finally {
			Writer out=resp.getWriter();
			out.write(JSONUtil.toJsonString(result));
			out.close();
		}
	}
}
