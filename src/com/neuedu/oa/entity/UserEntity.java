package com.neuedu.oa.entity;

import java.util.Date;

public class UserEntity {
	public static final int STATUS_NORMAL=0,	
			STATUS_PASSWORD_EXPIRED=1,
			STATUS_LOCKED=9;
	
	private String id;
	private String account;
	private String name;
	private String password;
	private Date createTime;
	private String lastLoginIP;
	private Date lastLoginTime;
	private Date lastModifyPasswordTime;
	private String phone;
	private String securityEmail;
	private String avatar;
	private String remark;
	private UserEntity createUser;
	/**
	 * 0=正常
	 * 1=强制锁定
	 * 2=密码过期锁定
	 */
	private int status;

	
	public boolean isLocked() {
		return this.status!=STATUS_NORMAL;
	}
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	
	
	
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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


	


	public UserEntity getCreateUser() {
		return createUser;
	}


	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}


	
	

}
