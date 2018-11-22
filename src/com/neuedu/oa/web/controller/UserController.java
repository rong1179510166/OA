package com.neuedu.oa.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.HandlerMapping;

import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/public-api/user/login")
	@ResponseBody
	public JSONResponse login(String account,String password,
			@RequestParam("captcha")String captchaInput,
			@SessionAttribute("captcha")String captcha,HttpServletRequest req) throws Exception {
		if (captcha != null) {
			if (!captcha.toString().equalsIgnoreCase(captchaInput)) {
				//throw new OAException("验证码输入错误，请重试");
			}
		}
		String loginIp = req.getRemoteHost();
		// 2 调用业务方法(进行转换)
		UserContext uc = userService.login(account, password, loginIp);
		// 3把当前登录用户上下文放入session中
		req.getSession().setAttribute(UserContext.class.getName(), uc);
		
		return JSONResponse.ok(uc);
	}
	
	protected JSONResponse logout(HttpServletRequest req) throws ServletException, IOException {
		HttpSession session=req.getSession(false);
		if(session!=null) {
			//说明之前访问过
			session.invalidate();
		}
		return JSONResponse.ok();	
	}

}
