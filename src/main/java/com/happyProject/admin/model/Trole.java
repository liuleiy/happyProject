package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_role")
public class Trole {
	private String id;
	private String memo;
	private String r_name;
	private Date ctime;
	private Date update_time;
	@Transient
	private Integer number;//非数据库字段，统计有多少个用户，是这个角色
	public Trole(String id, String memo, String r_name, Date ctime, Date update_time, Integer number) {
		super();
		this.id = id;
		this.memo = memo;
		this.r_name = r_name;
		this.ctime = ctime;
		this.update_time = update_time;
		this.number = number;
	}
	public Trole() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Trole [id=" + id + ", memo=" + memo + ", r_name=" + r_name + ", ctime=" + ctime + ", update_time="
				+ update_time + ", number=" + number + "]";
	}
	
}
