package com.happyProject.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.LogCoinDaoImpl;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.LogCoinService;

@Repository
public class LogCoinServiceImpl implements LogCoinService{
	@Resource
	private LogCoinDaoImpl logCoinDao;
	@Override
	public LogCoin AddAnaUpdate(LogCoin t) {
		return logCoinDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(LogCoin t) {
		logCoinDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(LogCoin t) {
		return logCoinDao.getAll(t);
	}

	@Override
	public int getCount(LogCoin t) {
		return logCoinDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, LogCoin t) {
		logCoinDao.updateById(id, map, t);
	}

	@Override
	public PageBean<LogCoin> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer type) {
		return logCoinDao.findByDataAndCount(userid, currentPage, pageSize, startTime, endTime,type);
	}

	@Override
	public LogCoin findById(Object id, LogCoin t) {
		return logCoinDao.findById(id, t);
	}

	@Override
	public List<LogCoin> getTimePump(Integer state, Integer end, Integer type, Date time) {
		return logCoinDao.getTimePump(state, end, type, time);
	}

}
