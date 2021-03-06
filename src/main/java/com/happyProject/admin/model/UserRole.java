package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_user_role")
public class UserRole {
	private String id;
	private String user_id;
	private String role_id;
	public UserRole(String id, String user_id, String role_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.role_id = role_id;
	}
	public UserRole() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", user_id=" + user_id + ", role_id=" + role_id + "]";
	}
	
}
