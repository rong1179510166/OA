package com.neuedu.oa.web.servlet.userhome;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.oa.Application;
import com.neuedu.oa.ao.ImageAo;
import com.neuedu.oa.service.UserService;
import com.neuedu.oa.service.impl.UserServiceImpl;
import com.neuedu.oa.web.servlet.AbstractHttpServlet;

@WebServlet("/avatar/*")
public class AvatarServlet extends AbstractHttpServlet{
	
	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String url=req.getRequestURI();
		String userId=url.substring(url.lastIndexOf('/')+1);
		System.out.println(userId+"---------------");
		ImageAo avatar=ImageAo.getDefaultAvatarAo();
		if(!"no-avatar.png".equalsIgnoreCase(userId)) {
			UserService userService=new UserServiceImpl();
			//avatar=userService.getUserAvatar(userId);	
		}
		String saveDir = Application.getString("user.avatar-save-dir", "/");
		
		resp.setContentType(avatar.getContentType());
		
		try(ServletOutputStream out = resp.getOutputStream();
				FileInputStream in=new FileInputStream(
						new File(saveDir,avatar.getName()));		
				){
			byte[] buf=new byte[10240];
			int readed=0;
			while((readed=in.read(buf))!=-1) {
				out.write(buf,0,readed);
			}
		}
		return null;
	}
}
