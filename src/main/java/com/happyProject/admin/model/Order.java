package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_trade_record")
public class Order {
	private String id;//商户订单号(游戏内自定义订单号)
	private String transid;//交易流水号(计费支付平台的交易流水号,微信订单号)
	private String userid; //用户,玩家在商户应用的唯一标识(userid)
	private String itemid;//购买商品ID
	private String amount;//购买商品数量
	private Long diamond;//购买钻石数量
	private Double money;//交易总金额(单位为分)
	private String transtime;//交易完成时间 yyyy-mm-dd hh24:mi:ss
	private Integer result;//交易结果(0–交易成功,1–交易失败,2-交易中,3-发货中)
	private Long waresid; //商品编码(平台为应用内需计费商品分配的编码)
	private String currency;//货币类型(RMB,CNY)
	private Integer transtype;//交易类型(0–支付交易
	private Integer feetype;//计费方式(表示商品采用的计费方式)
	private Integer paytype;//支付方式(表示用户采用的支付方式,403-微信支付)
	private String clientip;//客户端ip
	private String agent; //绑定的父级代理商游戏ID
	private Integer atype;//代理包类型
	private Integer first; //首次充值
	private Date utime;//本条记录更新unix时间戳
	private Date day_stamp;//Time Today
	private Date ctime;//本条记录生成unix时间戳
	public Order(String id, String transid, String userid, String itemid, String amount, Long diamond, Double money,
			String transtime, Integer result, Long waresid, String currency, Integer transtype, Integer feetype,
			Integer paytype, String clientip, String agent, Integer atype, Integer first, Date utime, Date day_stamp,
			Date ctime) {
		super();
		this.id = id;
		this.transid = transid;
		this.userid = userid;
		this.itemid = itemid;
		this.amount = amount;
		this.diamond = diamond;
		this.money = money;
		this.transtime = transtime;
		this.result = result;
		this.waresid = waresid;
		this.currency = currency;
		this.transtype = transtype;
		this.feetype = feetype;
		this.paytype = paytype;
		this.clientip = clientip;
		this.agent = agent;
		this.atype = atype;
		this.first = first;
		this.utime = utime;
		this.day_stamp = day_stamp;
		this.ctime = ctime;
	}
	public Order() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Long getDiamond() {
		return diamond;
	}
	public void setDiamond(Long diamond) {
		this.diamond = diamond;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Long getWaresid() {
		return waresid;
	}
	public void setWaresid(Long waresid) {
		this.waresid = waresid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getTranstype() {
		return transtype;
	}
	public void setTranstype(Integer transtype) {
		this.transtype = transtype;
	}
	public Integer getFeetype() {
		return feetype;
	}
	public void setFeetype(Integer feetype) {
		this.feetype = feetype;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getAtype() {
		return atype;
	}
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	public Date getDay_stamp() {
		return day_stamp;
	}
	public void setDay_stamp(Date day_stamp) {
		this.day_stamp = day_stamp;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", transid=" + transid + ", userid=" + userid + ", itemid=" + itemid + ", amount="
				+ amount + ", diamond=" + diamond + ", money=" + money + ", transtime=" + transtime + ", result="
				+ result + ", waresid=" + waresid + ", currency=" + currency + ", transtype=" + transtype + ", feetype="
				+ feetype + ", paytype=" + paytype + ", clientip=" + clientip + ", agent=" + agent + ", atype=" + atype
				+ ", first=" + first + ", utime=" + utime + ", day_stamp=" + day_stamp + ", ctime=" + ctime + "]";
	}
	
}
