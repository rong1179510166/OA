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

import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.service.impl.SystemServiceImpl;
import com.neuedu.oa.util.JSONUtil;
import com.neuedu.oa.web.servlet.AbstractAjaxHttpServlet;
@WebServlet("/api/system/user/get")
public class CheckServlet extends AbstractAjaxHttpServlet {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			// 1 获取参数(清理和验证参数)
			String id=req.getParameter("id");
			// 2 调用业务方法(进行转换)
			SystemService service=new SystemServiceImpl();
			UserEntity e=service.loadUser(id);
			// 3 返回结果
			Map<String,Object> dto=new HashMap<>(16,1);
			dto.put("id", e.getId());
			dto.put("name", e.getName());
			dto.put("account", e.getAccount());
			dto.put("avatar", e.getAvatar());
			dto.put("createTime", e.getCreateTime());
			dto.put("locked", e.isLocked());
			dto.put("lastLoginIp", e.getLastLoginIP());
			dto.put("lastLoginTime", e.getLastLoginTime());
			dto.put("lastModifyPasswordTime", e.getLastModifyPasswordTime());
			dto.put("phone", e.getPhone());
			dto.put("remark", e.getRemark());
			dto.put("securityEmail", e.getSecurityEmail());
			Map<String,Object> createUser=new HashMap<>(2,1);
			createUser.put("id", e.getCreateUser().getId());
			createUser.put("name", e.getCreateUser().getName());
			dto.put("createUser",createUser);
			dto.put("createUserId",e.getCreateUser().getId());
			return dto;
	}
	
	
}
