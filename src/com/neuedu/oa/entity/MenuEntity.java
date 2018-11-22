package com.neuedu.oa.entity;

import java.util.Date;

public class MenuEntity  {

	private String id;
	private String code;
	private String name;
	private String icon;
	private String url;
	private MenuEntity parent;
	//冗余
	private String parentId;
	private String remark;
	private int showOrder;
	private int flag;
	private UserEntity createUser;
	private String createUserId;
	private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MenuEntity getParent() {
		return parent;
	}
	public void setParent(MenuEntity parent) {
		this.parent = parent;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public UserEntity getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
