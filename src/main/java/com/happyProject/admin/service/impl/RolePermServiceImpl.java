package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.RolePermDaoImpl;
import com.happyProject.admin.model.RolePerm;
import com.happyProject.admin.service.RolePermService;

@Repository
public class RolePermServiceImpl implements RolePermService{
	@Resource
	private RolePermDaoImpl rolePermDao;

	@Override
	public RolePerm AddAnaUpdate(RolePerm t) {
		return rolePermDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(RolePerm t) {
		rolePermDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RolePerm t) {
		return rolePermDao.getAll(t);
	}

	@Override
	public int getCount(RolePerm t) {
		return rolePermDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RolePerm t) {
		rolePermDao.updateById(id, map, t);
	}

	@Override
	public RolePerm findById(Object id, RolePerm t) {
		return rolePermDao.findById(id, t);
	}

	@Override
	public List<RolePerm> findByMap(Integer currentPage, Integer pageSize, Map<String, Object> map) {
		return rolePermDao.findByMap(currentPage, pageSize, map);
	}

}
