package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.OperationLogDaoImpl;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.OperationLogService;

@Repository
public class OperationLogServiceImpl implements OperationLogService{

	@Resource
	private OperationLogDaoImpl operationLogDao;
	
	@Override
	public OperationLog AddAnaUpdate(OperationLog t) {
		return operationLogDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(OperationLog t) {
		operationLogDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(OperationLog t) {
		return operationLogDao.getAll(t);
	}

	@Override
	public int getCount(OperationLog t) {
		return operationLogDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, OperationLog t) {
		operationLogDao.updateById(id, map, t);
	}

	@Override
	public OperationLog findById(Object id, OperationLog t) {
		return operationLogDao.findById(id, t);
	}

	@Override
	public PageBean<OperationLog> findByDataAndCount(String username, Integer currentPage, Integer pageSize,
			Long startTime, Long endTime) {
		return operationLogDao.findByDataAndCount(username, currentPage, pageSize, startTime, endTime);
	}

}
