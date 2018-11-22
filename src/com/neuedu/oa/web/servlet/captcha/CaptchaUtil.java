package com.neuedu.oa.web.servlet.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CaptchaUtil {
	private static final String CHARS="ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
	public static String randomCode(int length) {
		char[] result=new char[length];
		for(int i=result.length;i-->0;) {
			result[i]=CHARS.charAt((int)(Math.random()*CHARS.length()));
		}
		return new String(result);
	}
	
	static public BufferedImage generate(String code,int width,int height) {
		BufferedImage img=new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = img.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
		
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("黑体", Font.BOLD, 30));
		g.drawString(code,20,20);
		g.setColor(Color.BLUE);
		g.drawArc(0, 0, width, height, 30, 60);
		return img;
	}
	public static void main(String[] args)throws Exception {
		BufferedImage img=generate(randomCode(6),200, 200);
		
		ImageIO.write(img, "png", new File("f:/1.png"));
	}
	
}
