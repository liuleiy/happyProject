package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tt_smsMessage")
public class SmsMessage {
	private String id;
	private String publisher;
	private Date ctime;
	private Integer recipientNumber;
	private String content;

	public SmsMessage(String id, String publisher, Date ctime, Integer recipientNumber, String content) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.ctime = ctime;
		this.recipientNumber = recipientNumber;
		this.content = content;
	}

	public SmsMessage() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Integer getRecipientNumber() {
		return recipientNumber;
	}

	public void setRecipientNumber(Integer recipientNumber) {
		this.recipientNumber = recipientNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SmsMessage [id=" + id + ", publisher=" + publisher + ", ctime=" + ctime + ", recipientNumber="
				+ recipientNumber + ", content=" + content + "]";
	}
}
