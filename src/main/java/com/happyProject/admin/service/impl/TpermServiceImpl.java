package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.TpermDaoImpl;
import com.happyProject.admin.model.Tperm;
import com.happyProject.admin.service.TpermService;

@Repository
public class TpermServiceImpl implements TpermService{

	@Resource
	private TpermDaoImpl tpermDaoImpl;
	
	@Override
	public Tperm AddAnaUpdate(Tperm t) {
		return tpermDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(Tperm t) {
		tpermDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Tperm t) {
		return tpermDaoImpl.getAll(t);
	}

	@Override
	public int getCount(Tperm t) {
		return tpermDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Tperm t) {
		tpermDaoImpl.updateById(id, map, t);
	}

	@Override
	public Tperm findById(Object id, Tperm t) {
		return tpermDaoImpl.findById(id, t);
	}

}
