package com.happyProject.admin.service;

import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Task;

public interface TaskService extends BaseDao<Task>{
	
	PageBean<Task> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
	List<Task> findByType(Integer type);
}
