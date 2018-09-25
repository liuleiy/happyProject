package com.happyProject.admin.model;

import java.util.ArrayList;
import java.util.List;

public class Condition {

	private Long startTime;
	private Long endTime;
	private String username;
	private String size;
	private String arrangement;
	private String roomid;
	private String name;
	private String userid;
	private String code;
	private String iid;
	private String nickname;
	private String publisher;
	private Integer rtype;
	private String agent;
	
	private String last_bring_profit;
	private String start_bring_profit;
	private String last_profit;
	private String start_profit;
	private Integer rtypeValue;
	
	private String check;//选中的ID
	private String agentID;//设置为上级的ID
	private String password;//
	private Integer currentPage;//当前页码
	private Integer pageSize;//每页记录条数
	private Integer del;//商品状态
	private Integer result;//订单状态
	private String idStr;//id的字符串
	private Integer agent_state;//
	private Integer status;//状态
	private String radio;//选中.弹出框
	private String radioMagess;//消息类型
	private Integer give;//数量。弹出框
	private String strCheck;//选中的人的ID
	private String content;//短信内容
	private Integer coin;
	private Integer diamond;
	private Integer day;
	private List<String> permRole = new ArrayList<String>();//角色所拥有的权限
	public Condition(Long startTime, Long endTime, String username, String size, String arrangement, String roomid,
			String name, String userid, String code, String iid, String nickname, String publisher, Integer rtype,
			String agent, String last_bring_profit, String start_bring_profit, String last_profit, String start_profit,
			Integer rtypeValue, String check, String agentID, String password, Integer currentPage, Integer pageSize,
			Integer del, Integer result, String idStr, Integer agent_state, Integer status, String radio,
			String radioMagess, Integer give, String strCheck, String content, Integer coin, Integer diamond,
			Integer day, List<String> permRole) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.username = username;
		this.size = size;
		this.arrangement = arrangement;
		this.roomid = roomid;
		this.name = name;
		this.userid = userid;
		this.code = code;
		this.iid = iid;
		this.nickname = nickname;
		this.publisher = publisher;
		this.rtype = rtype;
		this.agent = agent;
		this.last_bring_profit = last_bring_profit;
		this.start_bring_profit = start_bring_profit;
		this.last_profit = last_profit;
		this.start_profit = start_profit;
		this.rtypeValue = rtypeValue;
		this.check = check;
		this.agentID = agentID;
		this.password = password;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.del = del;
		this.result = result;
		this.idStr = idStr;
		this.agent_state = agent_state;
		this.status = status;
		this.radio = radio;
		this.radioMagess = radioMagess;
		this.give = give;
		this.strCheck = strCheck;
		this.content = content;
		this.coin = coin;
		this.diamond = diamond;
		this.day = day;
		this.permRole = permRole;
	}
	public Condition() {
		super();
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getArrangement() {
		return arrangement;
	}
	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Integer getRtype() {
		return rtype;
	}
	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getLast_bring_profit() {
		return last_bring_profit;
	}
	public void setLast_bring_profit(String last_bring_profit) {
		this.last_bring_profit = last_bring_profit;
	}
	public String getStart_bring_profit() {
		return start_bring_profit;
	}
	public void setStart_bring_profit(String start_bring_profit) {
		this.start_bring_profit = start_bring_profit;
	}
	public String getLast_profit() {
		return last_profit;
	}
	public void setLast_profit(String last_profit) {
		this.last_profit = last_profit;
	}
	public String getStart_profit() {
		return start_profit;
	}
	public void setStart_profit(String start_profit) {
		this.start_profit = start_profit;
	}
	public Integer getRtypeValue() {
		return rtypeValue;
	}
	public void setRtypeValue(Integer rtypeValue) {
		this.rtypeValue = rtypeValue;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public Integer getAgent_state() {
		return agent_state;
	}
	public void setAgent_state(Integer agent_state) {
		this.agent_state = agent_state;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getRadioMagess() {
		return radioMagess;
	}
	public void setRadioMagess(String radioMagess) {
		this.radioMagess = radioMagess;
	}
	public Integer getGive() {
		return give;
	}
	public void setGive(Integer give) {
		this.give = give;
	}
	public String getStrCheck() {
		return strCheck;
	}
	public void setStrCheck(String strCheck) {
		this.strCheck = strCheck;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public List<String> getPermRole() {
		return permRole;
	}
	public void setPermRole(List<String> permRole) {
		this.permRole = permRole;
	}
	@Override
	public String toString() {
		return "Condition [startTime=" + startTime + ", endTime=" + endTime + ", username=" + username + ", size="
				+ size + ", arrangement=" + arrangement + ", roomid=" + roomid + ", name=" + name + ", userid=" + userid
				+ ", code=" + code + ", iid=" + iid + ", nickname=" + nickname + ", publisher=" + publisher + ", rtype="
				+ rtype + ", agent=" + agent + ", last_bring_profit=" + last_bring_profit + ", start_bring_profit="
				+ start_bring_profit + ", last_profit=" + last_profit + ", start_profit=" + start_profit
				+ ", rtypeValue=" + rtypeValue + ", check=" + check + ", agentID=" + agentID + ", password=" + password
				+ ", currentPage=" + currentPage + ", pageSize=" + pageSize + ", del=" + del + ", result=" + result
				+ ", idStr=" + idStr + ", agent_state=" + agent_state + ", status=" + status + ", radio=" + radio
				+ ", radioMagess=" + radioMagess + ", give=" + give + ", strCheck=" + strCheck + ", content=" + content
				+ ", coin=" + coin + ", diamond=" + diamond + ", day=" + day + ", permRole=" + permRole + "]";
	}
	
	
}
