package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_role_perm")
public class RolePerm {
	private String id;
	private String role_id;
	private String perm_id;
	public RolePerm(String id, String role_id, String perm_id) {
		super();
		this.id = id;
		this.role_id = role_id;
		this.perm_id = perm_id;
	}
	public RolePerm() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getPerm_id() {
		return perm_id;
	}
	public void setPerm_id(String perm_id) {
		this.perm_id = perm_id;
	}
	@Override
	public String toString() {
		return "RolePerm [id=" + id + ", role_id=" + role_id + ", perm_id=" + perm_id + "]";
	}
	
}
