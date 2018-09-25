package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.RoundRecordDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoundRecord;
import com.happyProject.admin.service.RoundRecordService;

@Repository
public class RoundRecordServiceImpl implements RoundRecordService{
	@Resource
	private RoundRecordDaoImpl roundRecordDao;
	
	@Override
	public RoundRecord AddAnaUpdate(RoundRecord t) {
		return roundRecordDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(RoundRecord t) {
		roundRecordDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RoundRecord t) {
		return roundRecordDao.getAll(t);
	}

	@Override
	public int getCount(RoundRecord t) {
		return roundRecordDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RoundRecord t) {
		roundRecordDao.updateById(id, map, t);
	}

	@Override
	public PageBean<RoundRecord> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,String roomid) {
		return roundRecordDao.findByDataAndCount(currentPage, pageSize, startTime, endTime,roomid);
	}

	@Override
	public RoundRecord findById(Object id, RoundRecord t) {
		return roundRecordDao.findById(id, t);
	}

	@Override
	public List<RoundRecord> getNewTimeUser() {
		return roundRecordDao.getNewTimeUser();
	}
	
}
