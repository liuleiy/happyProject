package com.happyProject.admin.dao;

import java.util.Date;
import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoomResultRecord;

public interface RoomResultRecordDao extends BaseDao<RoomResultRecord>{
	
	PageBean<RoomResultRecord> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
	List<RoomResultRecord> getTimeRevenue(Integer state, Integer end,Date time,Long startTime,
			Long endTime);
	RoomResultRecord findOne(String rid);
}
