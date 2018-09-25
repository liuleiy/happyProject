package com.happyProject.admin.model;
/**
 * 登陆连续奖励
 * @author Administrator
 *
 */
public class LoginPrize {
	
	private String id;//unique ID
	private Integer day; //unique (0-6)
	private Integer del;//是否移除
	private Integer coin;//金币奖励
	private Integer diamond;//钻石奖励
	private String ctime;//创建时间
	public LoginPrize(String id, Integer day, Integer del, Integer coin,
			Integer diamond, String ctime) {
		super();
		this.id = id;
		this.day = day;
		this.del = del;
		this.coin = coin;
		this.diamond = diamond;
		this.ctime = ctime;
	}
	public LoginPrize() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public Integer getCoin() {
		return coin;
	}
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	public Integer getDiamond() {
		return diamond;
	}
	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "LoginPrize [id=" + id + ", day=" + day + ", del=" + del
				+ ", coin=" + coin + ", diamond=" + diamond + ", ctime="
				+ ctime + "]";
	}
	
}
