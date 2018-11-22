package com.neuedu.oa.web.servlet.usermanage;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.neuedu.oa.ao.QueryUserAo;
import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.QueryResult;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.service.impl.SystemServiceImpl;
import com.neuedu.oa.util.JSONUtil;
import com.neuedu.oa.web.servlet.AbstractAjaxHttpServlet;

@WebServlet("/api/system/user/list")
public class ListServlet extends AbstractAjaxHttpServlet {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		QueryUserAo ao = request2Ao(req, QueryUserAo.class);
		SystemService service = this.getBean(SystemService.class);
		
		UserContext uc =this.getCurrentUser(req);

		QueryResult<UserEntity> queryResult = service.queryUser(ao);
		return queryResult;
	}
}
