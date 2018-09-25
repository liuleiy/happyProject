package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

//登录日志
@Document(collection="col_log_login")
public class LoginLog {
	private String id;
	private String userid; //账户ID
	private Integer event; //事件：0=登录,1=正常退出,2＝系统关闭时被迫退出,3＝被动退出,4＝其它情况导致的退出
	private String ip;//登录IP
	private Date day_stamp;
	private Date login_time;
	private Date logout_time;
	private Integer atype;
	public LoginLog(String id, String userid, Integer event, String ip, Date day_stamp, Date login_time,
			Date logout_time, Integer atype) {
		super();
		this.id = id;
		this.userid = userid;
		this.event = event;
		this.ip = ip;
		this.day_stamp = day_stamp;
		this.login_time = login_time;
		this.logout_time = logout_time;
		this.atype = atype;
	}
	public LoginLog() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getEvent() {
		return event;
	}
	public void setEvent(Integer event) {
		this.event = event;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDay_stamp() {
		return day_stamp;
	}
	public void setDay_stamp(Date day_stamp) {
		this.day_stamp = day_stamp;
	}
	public Date getLogin_time() {
		return login_time;
	}
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	public Date getLogout_time() {
		return logout_time;
	}
	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}
	public Integer getAtype() {
		return atype;
	}
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	@Override
	public String toString() {
		return "LoginLog [id=" + id + ", userid=" + userid + ", event=" + event + ", ip=" + ip + ", day_stamp="
				+ day_stamp + ", login_time=" + login_time + ", logout_time=" + logout_time + ", atype=" + atype + "]";
	}
	
	
}
