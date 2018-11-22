package com.neuedu.oa.web.servlet.rolemanage;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.oa.entity.RoleEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.service.impl.SystemServiceImpl;
import com.neuedu.oa.web.servlet.AbstractHttpServlet;

@WebServlet("/system/role/list.do")
public class ListServlet extends AbstractHttpServlet {
	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			// 1 获取参数(清理和验证参数)
			SystemService service = new SystemServiceImpl();
			List<RoleEntity> queryResult = service.queryRole();
			// 3 进行视图处理
			req.setAttribute("rows", queryResult);
		} catch (OAException e) {
			req.setAttribute("message", e.getMessage());
		}
		return "/rolemanage/list.jsp";
	}
}
