package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_lucky")
public class Lucky {
	private String id; // unique ID
	private Integer luckyid; // unique
	private Integer gtype; // 游戏类型1 niu,2 san,3 jiu
	private String name; // 名称
	private Integer count; // 数值
	private Integer diamond; // 钻石奖励,欢乐
	private Integer coin; // 金币奖励
	private Integer del; // 是否移除
	private Date ctime; // 创建时间

	public Lucky(String id, Integer luckyid, Integer gtype, String name, Integer count, Integer diamond, Integer coin,
			Integer del, Date ctime) {
		super();
		this.id = id;
		this.luckyid = luckyid;
		this.gtype = gtype;
		this.name = name;
		this.count = count;
		this.diamond = diamond;
		this.coin = coin;
		this.del = del;
		this.ctime = ctime;
	}

	public Lucky() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLuckyid() {
		return luckyid;
	}

	public void setLuckyid(Integer luckyid) {
		this.luckyid = luckyid;
	}

	public Integer getGtype() {
		return gtype;
	}

	public void setGtype(Integer gtype) {
		this.gtype = gtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getDiamond() {
		return diamond;
	}

	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "Lucky [id=" + id + ", luckyid=" + luckyid + ", gtype=" + gtype + ", name=" + name + ", count=" + count
				+ ", diamond=" + diamond + ", coin=" + coin + ", del=" + del + ", ctime=" + ctime + "]";
	}
}
