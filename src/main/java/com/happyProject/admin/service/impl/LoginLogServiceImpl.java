package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.LoginLogDaoImpl;
import com.happyProject.admin.model.LoginLog;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.LoginLogService;

@Repository
public class LoginLogServiceImpl implements LoginLogService{
	@Resource
	private LoginLogDaoImpl loginLogDaoImpl;
	
	@Override
	public LoginLog AddAnaUpdate(LoginLog t) {
		return loginLogDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(LoginLog t) {
		loginLogDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(LoginLog t) {
		return loginLogDaoImpl.getAll(t);
	}

	@Override
	public int getCount(LoginLog t) {
		return loginLogDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, LoginLog t) {
		loginLogDaoImpl.updateById(id, map, t);
	}

	@Override
	public LoginLog findById(Object id, LoginLog t) {
		return loginLogDaoImpl.findById(id, t);
	}

	@Override
	public Integer getTotal(String userid) {
		return loginLogDaoImpl.getTotal(userid);
	}

	@Override
	public PageBean<LoginLog> findByDataAndCount(Integer currentPage, Integer pageSize, String userid) {
		return loginLogDaoImpl.findByDataAndCount(currentPage, pageSize, userid);
	}

}
