package com.happyProject.admin.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_user")
public class ColUser {
	private String id;//user的ID
	private String nickname;//昵称
	private String photo;//头像的地址
	private String wxuid;// 微信uid
	private int sex;//性别,男1 女2 非男非女3
	private String phone;//电话号码
	private String tourist;// 游客
	private String auth;// 密码验证码
	private String password;// MD5密码
	private String regist_ip;// 注册账户时的IP地址
	private String login_ip;// 登录账户时的IP地址
	private String ladelName;//标签
	private long diamond;//钻石
	private long coin;// 金币(欢乐豆)
	private long chip;//筹码
	private long card;//房卡
	private int vip;//vip等级
	private int status;//是否冻结
	private boolean robot;//是否是机器人
	private int win;//赢
	private int lost;//输
	private int ping;//平
	private int money;//钱 .充值总金额(分)
	private long top_diamonds;// 最高拥有钻石总金额
	private long top_coins;// 最高拥有金币总金额
	private long top_chips;// 最高拥有筹码总金额
	private long top_cards;// 最高拥有房卡总数
	private long top_win_diamond;// 单局赢最高钻石金额
	private long top_win_coin;// 单局赢最高金币金额
	private long top_win_chip; // 单局赢最高筹码金额
	private String agent;//绑定代理的ID
	private Date atime;// 绑定代理时间
	private Date ctime;//创建时间
	private Date login_time;
	//----------------------------------------------
	private Date agent_join_time;// 申请成为代理时间
	private Integer agent_state;// 是否是代理状态1通过
	private Integer agent_level;// 代理等级,1，2，3，4
	private Integer build; // 下属绑定数量
	private Integer agent_child;//下属代理数量
	private Integer build_vaild;//下属有效绑定数量
	private String agent_name;// 代理名字
	private String real_name;// 真实姓名
	private String weixin; // 微信
	private Integer profit_rate_sum;//分佣比例总数
	//private Double profit_rate;// 分佣比例
	private Map<String , Integer> profit_rate = new HashMap<String , Integer>();
	private Integer profit;// 收益
	private Integer profit_month;//月收益
	private Integer month;//当前月
	private Integer week_profit;// 本周收益
	private Integer week_player_profit; // 本周玩家收益
	private Date week_start;// 每周日重置
	private Date week_end;// 每周日重置
	private Integer history_profit;// 历史收益
	private Integer sub_player_profit;// 下属玩家业绩收益
	private Integer sub_agent_profit; // 下属代理业绩收益
	private Integer bank;  // 个人银行
	private String bank_phone;// 个人银行
	private String bank_password;// 个人银行
	private Integer login_times;//连续登录次数
	private Integer login_prize;//连续登录奖励
	private Integer loginloop;//连续登录循环
	private Integer bring_profit;//贡献上级的业绩
	private String sign;//个性签名
	private String lat;//Latitude
	private String lng;//Longitude
	private String address;//Address
	private Map<String, String> map = new HashMap<String, String>();
	public ColUser(String id, String nickname, String photo, String wxuid, int sex, String phone, String tourist,
			String auth, String password, String regist_ip, String login_ip, String ladelName, long diamond, long coin,
			long chip, long card, int vip, int status, boolean robot, int win, int lost, int ping, int money,
			long top_diamonds, long top_coins, long top_chips, long top_cards, long top_win_diamond, long top_win_coin,
			long top_win_chip, String agent, Date atime, Date ctime, Date login_time, Date agent_join_time,
			Integer agent_state, Integer agent_level, Integer build, Integer agent_child, Integer build_vaild,
			String agent_name, String real_name, String weixin, Integer profit_rate_sum,
			Map<String, Integer> profit_rate, Integer profit, Integer profit_month, Integer month, Integer week_profit,
			Integer week_player_profit, Date week_start, Date week_end, Integer history_profit,
			Integer sub_player_profit, Integer sub_agent_profit, Integer bank, String bank_phone, String bank_password,
			Integer login_times, Integer login_prize, Integer loginloop, Integer bring_profit, String sign, String lat,
			String lng, String address, Map<String, String> map) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.photo = photo;
		this.wxuid = wxuid;
		this.sex = sex;
		this.phone = phone;
		this.tourist = tourist;
		this.auth = auth;
		this.password = password;
		this.regist_ip = regist_ip;
		this.login_ip = login_ip;
		this.ladelName = ladelName;
		this.diamond = diamond;
		this.coin = coin;
		this.chip = chip;
		this.card = card;
		this.vip = vip;
		this.status = status;
		this.robot = robot;
		this.win = win;
		this.lost = lost;
		this.ping = ping;
		this.money = money;
		this.top_diamonds = top_diamonds;
		this.top_coins = top_coins;
		this.top_chips = top_chips;
		this.top_cards = top_cards;
		this.top_win_diamond = top_win_diamond;
		this.top_win_coin = top_win_coin;
		this.top_win_chip = top_win_chip;
		this.agent = agent;
		this.atime = atime;
		this.ctime = ctime;
		this.login_time = login_time;
		this.agent_join_time = agent_join_time;
		this.agent_state = agent_state;
		this.agent_level = agent_level;
		this.build = build;
		this.agent_child = agent_child;
		this.build_vaild = build_vaild;
		this.agent_name = agent_name;
		this.real_name = real_name;
		this.weixin = weixin;
		this.profit_rate_sum = profit_rate_sum;
		this.profit_rate = profit_rate;
		this.profit = profit;
		this.profit_month = profit_month;
		this.month = month;
		this.week_profit = week_profit;
		this.week_player_profit = week_player_profit;
		this.week_start = week_start;
		this.week_end = week_end;
		this.history_profit = history_profit;
		this.sub_player_profit = sub_player_profit;
		this.sub_agent_profit = sub_agent_profit;
		this.bank = bank;
		this.bank_phone = bank_phone;
		this.bank_password = bank_password;
		this.login_times = login_times;
		this.login_prize = login_prize;
		this.loginloop = loginloop;
		this.bring_profit = bring_profit;
		this.sign = sign;
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.map = map;
	}
	public ColUser() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getWxuid() {
		return wxuid;
	}
	public void setWxuid(String wxuid) {
		this.wxuid = wxuid;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTourist() {
		return tourist;
	}
	public void setTourist(String tourist) {
		this.tourist = tourist;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegist_ip() {
		return regist_ip;
	}
	public void setRegist_ip(String regist_ip) {
		this.regist_ip = regist_ip;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getLadelName() {
		return ladelName;
	}
	public void setLadelName(String ladelName) {
		this.ladelName = ladelName;
	}
	public long getDiamond() {
		return diamond;
	}
	public void setDiamond(long diamond) {
		this.diamond = diamond;
	}
	public long getCoin() {
		return coin;
	}
	public void setCoin(long coin) {
		this.coin = coin;
	}
	public long getChip() {
		return chip;
	}
	public void setChip(long chip) {
		this.chip = chip;
	}
	public long getCard() {
		return card;
	}
	public void setCard(long card) {
		this.card = card;
	}
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isRobot() {
		return robot;
	}
	public void setRobot(boolean robot) {
		this.robot = robot;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLost() {
		return lost;
	}
	public void setLost(int lost) {
		this.lost = lost;
	}
	public int getPing() {
		return ping;
	}
	public void setPing(int ping) {
		this.ping = ping;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public long getTop_diamonds() {
		return top_diamonds;
	}
	public void setTop_diamonds(long top_diamonds) {
		this.top_diamonds = top_diamonds;
	}
	public long getTop_coins() {
		return top_coins;
	}
	public void setTop_coins(long top_coins) {
		this.top_coins = top_coins;
	}
	public long getTop_chips() {
		return top_chips;
	}
	public void setTop_chips(long top_chips) {
		this.top_chips = top_chips;
	}
	public long getTop_cards() {
		return top_cards;
	}
	public void setTop_cards(long top_cards) {
		this.top_cards = top_cards;
	}
	public long getTop_win_diamond() {
		return top_win_diamond;
	}
	public void setTop_win_diamond(long top_win_diamond) {
		this.top_win_diamond = top_win_diamond;
	}
	public long getTop_win_coin() {
		return top_win_coin;
	}
	public void setTop_win_coin(long top_win_coin) {
		this.top_win_coin = top_win_coin;
	}
	public long getTop_win_chip() {
		return top_win_chip;
	}
	public void setTop_win_chip(long top_win_chip) {
		this.top_win_chip = top_win_chip;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Date getAtime() {
		return atime;
	}
	public void setAtime(Date atime) {
		this.atime = atime;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getLogin_time() {
		return login_time;
	}
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	public Date getAgent_join_time() {
		return agent_join_time;
	}
	public void setAgent_join_time(Date agent_join_time) {
		this.agent_join_time = agent_join_time;
	}
	public Integer getAgent_state() {
		return agent_state;
	}
	public void setAgent_state(Integer agent_state) {
		this.agent_state = agent_state;
	}
	public Integer getAgent_level() {
		return agent_level;
	}
	public void setAgent_level(Integer agent_level) {
		this.agent_level = agent_level;
	}
	public Integer getBuild() {
		return build;
	}
	public void setBuild(Integer build) {
		this.build = build;
	}
	public Integer getAgent_child() {
		return agent_child;
	}
	public void setAgent_child(Integer agent_child) {
		this.agent_child = agent_child;
	}
	public Integer getBuild_vaild() {
		return build_vaild;
	}
	public void setBuild_vaild(Integer build_vaild) {
		this.build_vaild = build_vaild;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public Integer getProfit_rate_sum() {
		return profit_rate_sum;
	}
	public void setProfit_rate_sum(Integer profit_rate_sum) {
		this.profit_rate_sum = profit_rate_sum;
	}
	public Map<String, Integer> getProfit_rate() {
		return profit_rate;
	}
	public void setProfit_rate(Map<String, Integer> profit_rate) {
		this.profit_rate = profit_rate;
	}
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	public Integer getProfit_month() {
		return profit_month;
	}
	public void setProfit_month(Integer profit_month) {
		this.profit_month = profit_month;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getWeek_profit() {
		return week_profit;
	}
	public void setWeek_profit(Integer week_profit) {
		this.week_profit = week_profit;
	}
	public Integer getWeek_player_profit() {
		return week_player_profit;
	}
	public void setWeek_player_profit(Integer week_player_profit) {
		this.week_player_profit = week_player_profit;
	}
	public Date getWeek_start() {
		return week_start;
	}
	public void setWeek_start(Date week_start) {
		this.week_start = week_start;
	}
	public Date getWeek_end() {
		return week_end;
	}
	public void setWeek_end(Date week_end) {
		this.week_end = week_end;
	}
	public Integer getHistory_profit() {
		return history_profit;
	}
	public void setHistory_profit(Integer history_profit) {
		this.history_profit = history_profit;
	}
	public Integer getSub_player_profit() {
		return sub_player_profit;
	}
	public void setSub_player_profit(Integer sub_player_profit) {
		this.sub_player_profit = sub_player_profit;
	}
	public Integer getSub_agent_profit() {
		return sub_agent_profit;
	}
	public void setSub_agent_profit(Integer sub_agent_profit) {
		this.sub_agent_profit = sub_agent_profit;
	}
	public Integer getBank() {
		return bank;
	}
	public void setBank(Integer bank) {
		this.bank = bank;
	}
	public String getBank_phone() {
		return bank_phone;
	}
	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}
	public String getBank_password() {
		return bank_password;
	}
	public void setBank_password(String bank_password) {
		this.bank_password = bank_password;
	}
	public Integer getLogin_times() {
		return login_times;
	}
	public void setLogin_times(Integer login_times) {
		this.login_times = login_times;
	}
	public Integer getLogin_prize() {
		return login_prize;
	}
	public void setLogin_prize(Integer login_prize) {
		this.login_prize = login_prize;
	}
	public Integer getLoginloop() {
		return loginloop;
	}
	public void setLoginloop(Integer loginloop) {
		this.loginloop = loginloop;
	}
	public Integer getBring_profit() {
		return bring_profit;
	}
	public void setBring_profit(Integer bring_profit) {
		this.bring_profit = bring_profit;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "ColUser [id=" + id + ", nickname=" + nickname + ", photo=" + photo + ", wxuid=" + wxuid + ", sex=" + sex
				+ ", phone=" + phone + ", tourist=" + tourist + ", auth=" + auth + ", password=" + password
				+ ", regist_ip=" + regist_ip + ", login_ip=" + login_ip + ", ladelName=" + ladelName + ", diamond="
				+ diamond + ", coin=" + coin + ", chip=" + chip + ", card=" + card + ", vip=" + vip + ", status="
				+ status + ", robot=" + robot + ", win=" + win + ", lost=" + lost + ", ping=" + ping + ", money="
				+ money + ", top_diamonds=" + top_diamonds + ", top_coins=" + top_coins + ", top_chips=" + top_chips
				+ ", top_cards=" + top_cards + ", top_win_diamond=" + top_win_diamond + ", top_win_coin=" + top_win_coin
				+ ", top_win_chip=" + top_win_chip + ", agent=" + agent + ", atime=" + atime + ", ctime=" + ctime
				+ ", login_time=" + login_time + ", agent_join_time=" + agent_join_time + ", agent_state=" + agent_state
				+ ", agent_level=" + agent_level + ", build=" + build + ", agent_child=" + agent_child
				+ ", build_vaild=" + build_vaild + ", agent_name=" + agent_name + ", real_name=" + real_name
				+ ", weixin=" + weixin + ", profit_rate_sum=" + profit_rate_sum + ", profit_rate=" + profit_rate
				+ ", profit=" + profit + ", profit_month=" + profit_month + ", month=" + month + ", week_profit="
				+ week_profit + ", week_player_profit=" + week_player_profit + ", week_start=" + week_start
				+ ", week_end=" + week_end + ", history_profit=" + history_profit + ", sub_player_profit="
				+ sub_player_profit + ", sub_agent_profit=" + sub_agent_profit + ", bank=" + bank + ", bank_phone="
				+ bank_phone + ", bank_password=" + bank_password + ", login_times=" + login_times + ", login_prize="
				+ login_prize + ", loginloop=" + loginloop + ", bring_profit=" + bring_profit + ", sign=" + sign
				+ ", lat=" + lat + ", lng=" + lng + ", address=" + address + ", map=" + map + "]";
	}
	
	
}
