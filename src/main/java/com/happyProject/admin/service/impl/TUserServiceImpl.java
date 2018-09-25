package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.TUserDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.service.TUserService;

@Repository
public class TUserServiceImpl implements TUserService{
	@Resource
	private TUserDaoImpl tUserDao;
	
	@Override
	public TUser AddAnaUpdate(TUser t) {
		return tUserDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(TUser t) {
		tUserDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(TUser t) {
		return tUserDao.getAll(t);
	}

	@Override
	public int getCount(TUser t) {
		return tUserDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, TUser t) {
		tUserDao.updateById(id, map, t);
	}

	@Override
	public TUser findById(Object id, TUser t) {
		return tUserDao.findById(id, t);
	}

	@Override
	public PageBean<TUser> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		
		return tUserDao.findByDataAndCount(currentPage, pageSize, startTime, endTime);
	}

	@Override
	public TUser login(String username, String password) {
		return tUserDao.login(username, password);
	}

}
