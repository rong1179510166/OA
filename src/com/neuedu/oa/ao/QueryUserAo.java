package com.neuedu.oa.ao;

public class QueryUserAo {
	private String key;
	private String pageNo;
	private String pageSize;
	private String state;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "QueryUserAo [key=" + key + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", state=" + state + "]";
	}
	
}
