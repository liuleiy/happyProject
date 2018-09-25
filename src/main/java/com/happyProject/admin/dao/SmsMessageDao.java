package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.SmsMessage;

public interface SmsMessageDao extends BaseDao<SmsMessage>{

	PageBean<SmsMessage> findByDataAndCount(String publisher,Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);

}
