package com.happyProject.admin.dao;

public interface SequenceDao {
	String nextId(String idValue);
	void nextOne(String idValue,String value);
}
