package com.neuedu.oa.dto;

import java.util.Date;

import com.neuedu.oa.entity.UserEntity;

public class UserContext {
	private static final ThreadLocal<UserContext> USER_THREAD_LOCAL=new ThreadLocal<>();
	private String id;
	private String account;
	private String name;
	private Date createTime;
	private String lastLoginIP;
	private Date lastLoginTime;
	private Date lastModifyPasswordTime;
	private String phone;
	private String securityEmail;
	private String avatar;
	private String remark;
	private int flag;
	private String createUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLastModifyPasswordTime() {
		return lastModifyPasswordTime;
	}
	public void setLastModifyPasswordTime(Date lastModifyPasswordTime) {
		this.lastModifyPasswordTime = lastModifyPasswordTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSecurityEmail() {
		return securityEmail;
	}
	public void setSecurityEmail(String securityEmail) {
		this.securityEmail = securityEmail;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public static UserContext getCurrent() {
		UserContext uc=USER_THREAD_LOCAL.get();
		return uc;
	}
	
	public static String getCurrentUserId() {
		UserContext uc=USER_THREAD_LOCAL.get();
		return uc.getId();
	}
	
	public static void setCurrent(UserContext uc) {
		USER_THREAD_LOCAL.set(uc);
	}
	
	
}
