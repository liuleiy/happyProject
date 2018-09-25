package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_activity")
public class Activity {
	private String id;// ID
	private Integer type;// 类型0,1,2
	private Integer del;// 是否移除
	private String title;// 标题内容
	private String content;// 活动内容
	private Date start_time;// 开始时间
	private Date end_time;// 结束时间
	private Date ctime; // 创建时间

	public Activity(String id, Integer type, Integer del, String title, String content, Date start_time, Date end_time,
			Date ctime) {
		super();
		this.id = id;
		this.type = type;
		this.del = del;
		this.title = title;
		this.content = content;
		this.start_time = start_time;
		this.end_time = end_time;
		this.ctime = ctime;
	}

	public Activity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", type=" + type + ", del=" + del + ", title=" + title + ", content=" + content
				+ ", start_time=" + start_time + ", end_time=" + end_time + ", ctime=" + ctime + "]";
	}
}
