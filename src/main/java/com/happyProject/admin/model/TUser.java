package com.happyProject.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_user")
public class TUser {
	private String id;//t_user的ID
	private String user_name;//账号
	private String password;//密码
	@Transient
	private String password2;//确认密码,非数据库字段
	private Integer sex;//性别
	private String email;//email地址
	private String last_login;//登录时间.有问题
	private String last_ip;//登录IP
	private Integer status;//状态,0正常，1禁止，2为审核
	private Date create_time;//创建时间
	private Date update_time;//修改时间
	private List<Trole> role_list = new ArrayList<Trole>();
	private String phone;//电话
	private String photo;//图片地址
	private String r_name;//角色名称
	private String oldRname;//旧的角色名称
	public TUser(String id, String user_name, String password, String password2, Integer sex, String email,
			String last_login, String last_ip, Integer status, Date create_time, Date update_time,
			List<Trole> role_list, String phone, String photo, String r_name, String oldRname) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.password2 = password2;
		this.sex = sex;
		this.email = email;
		this.last_login = last_login;
		this.last_ip = last_ip;
		this.status = status;
		this.create_time = create_time;
		this.update_time = update_time;
		this.role_list = role_list;
		this.phone = phone;
		this.photo = photo;
		this.r_name = r_name;
		this.oldRname = oldRname;
	}
	public TUser() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}
	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public List<Trole> getRole_list() {
		return role_list;
	}
	public void setRole_list(List<Trole> role_list) {
		this.role_list = role_list;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public String getOldRname() {
		return oldRname;
	}
	public void setOldRname(String oldRname) {
		this.oldRname = oldRname;
	}
	@Override
	public String toString() {
		return "TUser [id=" + id + ", user_name=" + user_name + ", password=" + password + ", password2=" + password2
				+ ", sex=" + sex + ", email=" + email + ", last_login=" + last_login + ", last_ip=" + last_ip
				+ ", status=" + status + ", create_time=" + create_time + ", update_time=" + update_time
				+ ", role_list=" + role_list + ", phone=" + phone + ", photo=" + photo + ", r_name=" + r_name
				+ ", oldRname=" + oldRname + "]";
	}
	
}
