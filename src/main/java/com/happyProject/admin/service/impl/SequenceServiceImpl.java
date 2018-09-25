package com.happyProject.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.SequenceDaoImpl;
import com.happyProject.admin.service.SequenceService;

@Repository
public class SequenceServiceImpl implements SequenceService{

	@Resource
	private SequenceDaoImpl sequenceDao;
	
	@Override
	public String nextId(String idValue) {
		return sequenceDao.nextId(idValue);
	}

	@Override
	public void nextOne(String idValue,String value) {
		sequenceDao.nextOne(idValue,value);
	}

}
