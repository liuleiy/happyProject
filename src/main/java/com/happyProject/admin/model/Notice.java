package com.happyProject.admin.model;


public class Notice {
	private String id;//公告ID
	private String Userid;//玩家
	private Integer rtype;//类型,1=公告消息,2=广播消息,rtype消息类型，0玩家个人购买消息，1公告消息，2广播消息，3系统消息
	private Integer atype;//分包类型
	private Integer acttype;//操作类型,0=无操作,1=支付,2=活动,
	private Integer top;//置顶
	private Integer num;//广播次数
	private Integer del;//是否移除
	private String content;//广播内容
	private String etime;//过期时间
	private String ctime; //创建时间
	public Notice(String id, String userid, Integer rtype, Integer atype,
			Integer acttype, Integer top, Integer num, Integer del,
			String content, String etime, String ctime) {
		super();
		this.id = id;
		Userid = userid;
		this.rtype = rtype;
		this.atype = atype;
		this.acttype = acttype;
		this.top = top;
		this.num = num;
		this.del = del;
		this.content = content;
		this.etime = etime;
		this.ctime = ctime;
	}
	public Notice() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	public Integer getRtype() {
		return rtype;
	}
	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}
	public Integer getAtype() {
		return atype;
	}
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	public Integer getActtype() {
		return acttype;
	}
	public void setActtype(Integer acttype) {
		this.acttype = acttype;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return "Notice [id=" + id + ", Userid=" + Userid + ", rtype=" + rtype
				+ ", atype=" + atype + ", acttype=" + acttype + ", top=" + top
				+ ", num=" + num + ", del=" + del + ", content=" + content
				+ ", etime=" + etime + ", ctime=" + ctime + "]";
	}
	
	/*public Notice(String id, String userid, Integer rtype, Integer atype,
			Integer acttype, Integer top, Integer num, Integer del,
			String content, Date etime, Date ctime) {
		super();
		this.id = id;
		Userid = userid;
		this.rtype = rtype;
		this.atype = atype;
		this.acttype = acttype;
		this.top = top;
		this.num = num;
		this.del = del;
		this.content = content;
		this.etime = etime;
		this.ctime = ctime;
	}
	public Notice() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	public Integer getRtype() {
		return rtype;
	}
	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}
	public Integer getAtype() {
		return atype;
	}
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	public Integer getActtype() {
		return acttype;
	}
	public void setActtype(Integer acttype) {
		this.acttype = acttype;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "Notice [id=" + id + ", Userid=" + Userid + ", rtype=" + rtype
				+ ", atype=" + atype + ", acttype=" + acttype + ", top=" + top
				+ ", num=" + num + ", del=" + del + ", content=" + content
				+ ", etime=" + etime + ", ctime=" + ctime + "]";
	}*/
	
}
