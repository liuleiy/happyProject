package com.happyProject.admin.dao;

import java.util.Date;
import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.PageBean;

public interface LogCoinDao extends BaseDao<LogCoin> {
	PageBean<LogCoin> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer type);
	List<LogCoin> getTimePump(Integer state, Integer end, Integer type,
			Date time);
}
