package com.happyProject.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.RoomResultRecordDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.service.RoomResultRecordService;

@Repository
public class RoomResultRecordServiceImpl implements RoomResultRecordService{

	@Resource
	private RoomResultRecordDaoImpl roomResultRecordDao;
	
	@Override
	public RoomResultRecord AddAnaUpdate(RoomResultRecord t) {
		return roomResultRecordDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(RoomResultRecord t) {
		roomResultRecordDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RoomResultRecord t) {
		return roomResultRecordDao.getAll(t);
	}

	@Override
	public int getCount(RoomResultRecord t) {
		return roomResultRecordDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RoomResultRecord t) {
		roomResultRecordDao.updateById(id, map, t);
	}

	@Override
	public PageBean<RoomResultRecord> findByDataAndCount(String userid, Integer currentPage, Integer pageSize,
			Long startTime, Long endTime) {
		return roomResultRecordDao.findByDataAndCount(userid, currentPage, pageSize, startTime, endTime);
	}

	@Override
	public RoomResultRecord findById(Object id, RoomResultRecord t) {
		return roomResultRecordDao.findById(id, t);
	}

	@Override
	public List<RoomResultRecord> getTimeRevenue(Integer state, Integer end, Date time, Long startTime, Long endTime) {
		return roomResultRecordDao.getTimeRevenue(state, end, time,startTime,endTime);
	}

	@Override
	public RoomResultRecord findOne(String rid) {
		return roomResultRecordDao.findOne(rid);
	}

}
