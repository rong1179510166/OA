package com.neuedu.oa.ao;

import java.io.Serializable;

public class UserAo implements Serializable {
	
	private String account;
	private String name;
	private String remark;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
