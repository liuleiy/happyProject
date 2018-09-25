package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.TaskDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Task;
import com.happyProject.admin.service.TaskService;

@Repository
public class TaskServiceImpl implements TaskService {
	@Resource
	private TaskDaoImpl taskDaoImpl;

	@Override
	public Task AddAnaUpdate(Task t) {
		return taskDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(Task t) {
		taskDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Task t) {
		return taskDaoImpl.getAll(t);
	}

	@Override
	public int getCount(Task t) {
		return taskDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Task t) {
		taskDaoImpl.updateById(id, map, t);
	}

	@Override
	public PageBean<Task> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		return taskDaoImpl.findByDataAndCount(currentPage, pageSize, startTime, endTime);
	}

	@Override
	public Task findById(Object id, Task t) {
		return taskDaoImpl.findById(id, t);
	}

	@Override
	public List<Task> findByType(Integer type) {
		
		return taskDaoImpl.findByType(type);
	}

}
