package com.dbsun.entity;

import org.apache.ibatis.type.Alias;

/**
 * 登录用户表
 *
 */
@Alias("Test")
public class Test {

	private int userid;		
	private String username;
	private String password;
	private int status;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
