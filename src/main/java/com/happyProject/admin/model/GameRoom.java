package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="col_game")
public class GameRoom {
	private String id;
	private int gtype;//游戏类型1 niu,2 san,3 jiu
	private int rtype;//房间类型0免佣,1抽佣
	private int dtype;//桌子类型
	private int ltype;//彩票类型1bjpk10,1mlaft
	private String name;//房间名称
	private long status;//房间状态1打开,2关闭,3隐藏
	private long count;//房间限制人数
	private long ante;//房间底分
	private long cost;//房间抽佣百分比
	private long vip;//房间vip限制
	private long chip;//房间进入筹码限制
	private boolean deal;//房间是否可以上庄
	private long carry;//房间上庄最小携带筹码限制
	private long down;//房间下庄最小携带筹码限制
	private long top; //房间下庄最大携带筹码限制
	private long sit;//房间内坐下限制
	private int del; //是否移除
	private String node;//所在节点(game.huiyin1|game.huiyin2)
	private Date ctime; //创建时间
	private long num;//启动房间数量
	private long minimum;//房间最低限制
	private long maximum;//房间最高限制
	public GameRoom(String id, int gtype, int rtype, int dtype, int ltype, String name, long status, long count,
			long ante, long cost, long vip, long chip, boolean deal, long carry, long down, long top, long sit, int del,
			String node, Date ctime, long num, long minimum, long maximum) {
		super();
		this.id = id;
		this.gtype = gtype;
		this.rtype = rtype;
		this.dtype = dtype;
		this.ltype = ltype;
		this.name = name;
		this.status = status;
		this.count = count;
		this.ante = ante;
		this.cost = cost;
		this.vip = vip;
		this.chip = chip;
		this.deal = deal;
		this.carry = carry;
		this.down = down;
		this.top = top;
		this.sit = sit;
		this.del = del;
		this.node = node;
		this.ctime = ctime;
		this.num = num;
		this.minimum = minimum;
		this.maximum = maximum;
	}
	public GameRoom() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGtype() {
		return gtype;
	}
	public void setGtype(int gtype) {
		this.gtype = gtype;
	}
	public int getRtype() {
		return rtype;
	}
	public void setRtype(int rtype) {
		this.rtype = rtype;
	}
	public int getDtype() {
		return dtype;
	}
	public void setDtype(int dtype) {
		this.dtype = dtype;
	}
	public int getLtype() {
		return ltype;
	}
	public void setLtype(int ltype) {
		this.ltype = ltype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getAnte() {
		return ante;
	}
	public void setAnte(long ante) {
		this.ante = ante;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
	public long getVip() {
		return vip;
	}
	public void setVip(long vip) {
		this.vip = vip;
	}
	public long getChip() {
		return chip;
	}
	public void setChip(long chip) {
		this.chip = chip;
	}
	public boolean isDeal() {
		return deal;
	}
	public void setDeal(boolean deal) {
		this.deal = deal;
	}
	public long getCarry() {
		return carry;
	}
	public void setCarry(long carry) {
		this.carry = carry;
	}
	public long getDown() {
		return down;
	}
	public void setDown(long down) {
		this.down = down;
	}
	public long getTop() {
		return top;
	}
	public void setTop(long top) {
		this.top = top;
	}
	public long getSit() {
		return sit;
	}
	public void setSit(long sit) {
		this.sit = sit;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public long getMinimum() {
		return minimum;
	}
	public void setMinimum(long minimum) {
		this.minimum = minimum;
	}
	public long getMaximum() {
		return maximum;
	}
	public void setMaximum(long maximum) {
		this.maximum = maximum;
	}
	@Override
	public String toString() {
		return "GameRoom [id=" + id + ", gtype=" + gtype + ", rtype=" + rtype + ", dtype=" + dtype + ", ltype=" + ltype
				+ ", name=" + name + ", status=" + status + ", count=" + count + ", ante=" + ante + ", cost=" + cost
				+ ", vip=" + vip + ", chip=" + chip + ", deal=" + deal + ", carry=" + carry + ", down=" + down
				+ ", top=" + top + ", sit=" + sit + ", del=" + del + ", node=" + node + ", ctime=" + ctime + ", num="
				+ num + ", minimum=" + minimum + ", maximum=" + maximum + "]";
	}
	
}
