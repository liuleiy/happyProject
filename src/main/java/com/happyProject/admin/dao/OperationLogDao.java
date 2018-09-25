package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;

public interface OperationLogDao extends BaseDao<OperationLog>{
	PageBean<OperationLog> findByDataAndCount(String username, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
}
