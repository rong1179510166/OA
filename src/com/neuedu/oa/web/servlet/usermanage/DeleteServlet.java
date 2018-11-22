package com.neuedu.oa.web.servlet.usermanage;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.oa.ao.UserAo;
import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.service.impl.SystemServiceImpl;
import com.neuedu.oa.util.JSONUtil;
import com.neuedu.oa.web.servlet.AbstractAjaxHttpServlet;
@WebServlet("/api/system/user/del")
public class DeleteServlet extends AbstractAjaxHttpServlet {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			// 1 获取参数(清理和验证参数)
			String id=req.getParameter("id");
			// 2 调用业务方法(进行转换)
			SystemService service=new SystemServiceImpl();
			service.deleteUser(id);
			return null;
	}
}
