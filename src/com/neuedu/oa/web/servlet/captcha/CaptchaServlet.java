package com.neuedu.oa.web.servlet.captcha;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.oa.web.servlet.AbstractHttpServlet;
@WebServlet("/public-api/captcha")
public class CaptchaServlet extends AbstractHttpServlet {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String code=CaptchaUtil.randomCode(6);
		req.getSession().setAttribute("captcha", code);
		BufferedImage img = CaptchaUtil.generate(code,150, 40);
		resp.setContentType("image/png");
		try (ServletOutputStream out = resp.getOutputStream();) {
			ImageIO.write(img, "png", out);
		}
		return null;
	}
}
