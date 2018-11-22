package com.neuedu.oa.ao;

public class ImageAo {
	private String name;
	private String fileSufix;
	private long length;
	private String contentType;
	private int width;
	private int height;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileSufix() {
		return fileSufix;
	}
	public void setFileSufix(String fileSufix) {
		this.fileSufix = fileSufix;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public static ImageAo getDefaultAvatarAo() {
		ImageAo ao=new ImageAo();
		ao.setContentType("image/png");
		ao.setName("no-avatar.png");
		ao.setFileSufix(".png");
		ao.setHeight(215);
		ao.setLength(27738);
		ao.setWidth(215);
		return ao;
	}
}
