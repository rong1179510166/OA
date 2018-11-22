package com.neuedu.oa.entity;

import java.util.Date;

public class RoleEntity {
	private String id;
	private String code;
	private String name;
	private String remark;
	private UserEntity createUser;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", code=" + code + ", name=" + name + ", remark=" + remark + ", createUser="
				+ createUser + ", createTime=" + createTime + "]";
	}

	
}
