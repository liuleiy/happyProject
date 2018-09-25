package com.happyProject.admin.model;

/**
 * 同步变动货币数据(充值或后台操作等)
 * @author Administrator
 *
 */
public class PayCurrency {
	private String userid;
	private Integer type;//变动类型
	private Integer coin;//变动金币数量
	private Integer diamond;//变动钻石数量
	private Integer chip;//变动筹码数量
	private Integer card;//变动房卡数量
	private Integer money;//变动充值数量
	public PayCurrency(String userid, Integer type, Integer coin,
			Integer diamond, Integer chip, Integer card, Integer money) {
		super();
		this.userid = userid;
		this.type = type;
		this.coin = coin;
		this.diamond = diamond;
		this.chip = chip;
		this.card = card;
		this.money = money;
	}
	public PayCurrency() {
		super();
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getChip() {
		return chip;
	}
	public void setChip(Integer chip) {
		this.chip = chip;
	}
	public Integer getCard() {
		return card;
	}
	public void setCard(Integer card) {
		this.card = card;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "PayCurrency [userid=" + userid + ", type=" + type + ", coin="
				+ coin + ", diamond=" + diamond + ", chip=" + chip + ", card="
				+ card + ", money=" + money + "]";
	}
	
	/*string Userid = 1;
    int32 Type = 2;//变动类型
    int64 Coin = 3;//变动金币数量
    int64 Diamond = 4;//变动钻石数量
    int64 Chip = 5;//变动筹码数量
    int64 Card = 6;//变动房卡数量
    int64 Money = 7;//变动充值数量
*/}
