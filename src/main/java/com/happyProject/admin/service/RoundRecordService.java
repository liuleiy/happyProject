package com.happyProject.admin.service;

import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoundRecord;

public interface RoundRecordService extends BaseDao<RoundRecord>{

	PageBean<RoundRecord> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,String roomid);
	List<RoundRecord> getNewTimeUser();
	
}
