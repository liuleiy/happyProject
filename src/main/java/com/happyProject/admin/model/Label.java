package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

//标签
@Document(collection = "tt_label")
public class Label {
	private String id;
	private String labelname;// 标签名称
	private Integer tcount;// 设置的订单数
	private Double tmoney;// 订单数所需要到达的钱数
	public Label(String id, String labelname, Integer tcount, Double tmoney) {
		super();
		this.id = id;
		this.labelname = labelname;
		this.tcount = tcount;
		this.tmoney = tmoney;
	}
	public Label() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelname() {
		return labelname;
	}
	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}
	public Integer getTcount() {
		return tcount;
	}
	public void setTcount(Integer tcount) {
		this.tcount = tcount;
	}
	public Double getTmoney() {
		return tmoney;
	}
	public void setTmoney(Double tmoney) {
		this.tmoney = tmoney;
	}
	@Override
	public String toString() {
		return "Label [id=" + id + ", labelname=" + labelname + ", tcount=" + tcount + ", tmoney=" + tmoney + "]";
	}

	
}
