package com.neuedu.oa.web.servlet.userhome;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.neuedu.oa.Application;
import com.neuedu.oa.ao.ImageAo;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.UserService;
import com.neuedu.oa.service.impl.UserServiceImpl;
import com.neuedu.oa.util.JSONUtil;
import com.neuedu.oa.web.servlet.AbstractAjaxHttpServlet;

@WebServlet("/user/modifyAvatar.do")
@MultipartConfig(maxRequestSize = 1024 * 1024 * 2)
public class ModifyMyAvatarServlet extends AbstractAjaxHttpServlet {
	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 1获取当前用户id
		HttpSession session = req.getSession();
		UserContext uc = (UserContext) session.getAttribute(UserContext.class.getName());
		String currentUserId = uc.getId();
		// 2获取上传的头像
		// 2.1 获取保存路径
		String saveDir = Application.getString("user.avatar-save-dir", "/");
		Part avatarPart = req.getPart("avatar");
				
		// 替换contentType中的斜杠
		String fileName = avatarPart.getSubmittedFileName();
		String fileSufix = fileName.substring(fileName.lastIndexOf('.'));
		String saveFileName = currentUserId ;
		String savePath = new File(saveDir, saveFileName).getCanonicalPath();
		avatarPart.write(savePath);
		// 3调用业务方法
		UserService service = new UserServiceImpl();
		ImageAo ao=new ImageAo();
		ao.setContentType(avatarPart.getContentType());
		ao.setFileSufix(fileSufix);
		ao.setName(currentUserId);
		ao.setLength(avatarPart.getSize());
		
		service.modifyUserAvatar(currentUserId,ao);
		
		return null;
	}

}
