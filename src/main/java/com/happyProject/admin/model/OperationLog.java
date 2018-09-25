package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/*
 * 操作日志
 */
@Document(collection="tt_operation_log")
public class OperationLog {
	private String id;
	private String actor;//谁
	private String ip;
	private String action;//动作
	private Date ctime;//创建时间
	public OperationLog(String id, String actor, String ip, String action, Date ctime) {
		super();
		this.id = id;
		this.actor = actor;
		this.ip = ip;
		this.action = action;
		this.ctime = ctime;
	}
	public OperationLog() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "OperationLog [id=" + id + ", actor=" + actor + ", ip=" + ip + ", action=" + action + ", ctime=" + ctime
				+ "]";
	}
	
}
