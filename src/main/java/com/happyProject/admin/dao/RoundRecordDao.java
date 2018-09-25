package com.happyProject.admin.dao;

import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoundRecord;

public interface RoundRecordDao extends BaseDao<RoundRecord>{

	PageBean<RoundRecord> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,String roomid);
	List<RoundRecord> getNewTimeUser();
}
