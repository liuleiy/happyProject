package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_perm")
public class Tperm {
	private String id;
	private String module;
	private String action;
	public Tperm(String id, String module, String action) {
		super();
		this.id = id;
		this.module = module;
		this.action = action;
	}
	public Tperm() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String toString() {
		return "Tperm [id=" + id + ", module=" + module + ", action=" + action + "]";
	}
	
}
