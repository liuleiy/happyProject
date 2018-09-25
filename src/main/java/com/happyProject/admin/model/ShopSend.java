package com.happyProject.admin.model;

public class ShopSend {
	private String id;//购买ID
	private Integer status;//物品状态,1=热卖
	private Integer propid;//兑换的物品,1=钻石,2=金币
	private Integer payway;//支付方式,1=RMB,,2=钻石
	private Long number;//兑换的数量
	private Double price;//支付价格(单位元)
	private String name;//物品名字
	private String info;//物品信息
	private Integer del;//是否移除
	private String etime;//过期时间
	private String ctime;//创建时间
	public ShopSend(String id, Integer status, Integer propid, Integer payway,
			Long number, Double price, String name, String info, Integer del,
			String etime, String ctime) {
		super();
		this.id = id;
		this.status = status;
		this.propid = propid;
		this.payway = payway;
		this.number = number;
		this.price = price;
		this.name = name;
		this.info = info;
		this.del = del;
		this.etime = etime;
		this.ctime = ctime;
	}
	public ShopSend() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPropid() {
		return propid;
	}
	public void setPropid(Integer propid) {
		this.propid = propid;
	}
	public Integer getPayway() {
		return payway;
	}
	public void setPayway(Integer payway) {
		this.payway = payway;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "ShopSend [id=" + id + ", status=" + status + ", propid="
				+ propid + ", payway=" + payway + ", number=" + number
				+ ", price=" + price + ", name=" + name + ", info=" + info
				+ ", del=" + del + ", etime=" + etime + ", ctime=" + ctime
				+ "]";
	}
	
}
