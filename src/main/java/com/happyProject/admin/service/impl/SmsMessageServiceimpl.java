package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.SmsMessageDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.SmsMessage;
import com.happyProject.admin.service.SmsMessageService;

@Repository
public class SmsMessageServiceimpl implements SmsMessageService {
	@Resource
	private SmsMessageDaoImpl smsMessageDao;

	@Override
	public SmsMessage AddAnaUpdate(SmsMessage t) {
		return smsMessageDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(SmsMessage t) {
		smsMessageDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(SmsMessage t) {
		return smsMessageDao.getAll(t);
	}

	@Override
	public int getCount(SmsMessage t) {
		return smsMessageDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, SmsMessage t) {
		smsMessageDao.updateById(id, map, t);
	}

	@Override
	public PageBean<SmsMessage> findByDataAndCount(String publisher, Integer currentPage, Integer pageSize,
			Long startTime, Long endTime) {
		return smsMessageDao.findByDataAndCount(publisher, currentPage, pageSize, startTime, endTime);
	}

	@Override
	public SmsMessage findById(Object id, SmsMessage t) {
		return smsMessageDao.findById(id, t);
	}

}
