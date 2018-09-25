package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Lucky;
import com.happyProject.admin.model.PageBean;

public interface LuckyService extends BaseDao<Lucky>{
	PageBean<Lucky> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
	Lucky isLuckid(String fieldName , Object value);
}
