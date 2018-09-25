package com.happyProject.admin.service;

public interface SequenceService {
	String nextId(String idValue);
	void nextOne(String idValue,String value);
}
