package com.happyProject.admin.model;



public class TaskSend {

	private String id;
	private Integer taskid;
	private Integer type;
	private String name;
	private Integer count;
	private Long diamond;
	private Long coin;
	private Boolean today;
	private Integer del;
	private Integer nextid;
	private String ctime;
	public TaskSend(String id, Integer taskid, Integer type, String name,
			Integer count, Long diamond, Long coin, Boolean today, Integer del,
			Integer nextid, String ctime) {
		super();
		this.id = id;
		this.taskid = taskid;
		this.type = type;
		this.name = name;
		this.count = count;
		this.diamond = diamond;
		this.coin = coin;
		this.today = today;
		this.del = del;
		this.nextid = nextid;
		this.ctime = ctime;
	}
	public TaskSend() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getTaskid() {
		return taskid;
	}
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Long getDiamond() {
		return diamond;
	}
	public void setDiamond(Long diamond) {
		this.diamond = diamond;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	public Boolean getToday() {
		return today;
	}
	public void setToday(Boolean today) {
		this.today = today;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public Integer getNextid() {
		return nextid;
	}
	public void setNextid(Integer nextid) {
		this.nextid = nextid;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "TaskSend [id=" + id + ", taskid=" + taskid + ", type=" + type
				+ ", name=" + name + ", count=" + count + ", diamond="
				+ diamond + ", coin=" + coin + ", today=" + today + ", del="
				+ del + ", nextid=" + nextid + ", ctime=" + ctime + "]";
	}
	

}
