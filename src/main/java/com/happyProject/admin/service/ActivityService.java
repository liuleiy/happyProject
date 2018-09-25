package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Activity;
import com.happyProject.admin.model.PageBean;

public interface ActivityService extends BaseDao<Activity>{
	
	PageBean<Activity> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);

}
