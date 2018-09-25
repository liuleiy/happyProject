package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_sequence")
public class Sequence {
	private String id;
	private String value;
	public Sequence(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public Sequence() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Sequence [id=" + id + ", value=" + value + "]";
	}
	
}
