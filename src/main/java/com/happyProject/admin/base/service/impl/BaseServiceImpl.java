package com.happyProject.admin.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.happyProject.admin.base.dao.impl.BaseDaoImpl;
import com.happyProject.admin.base.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{
	@Resource
	private BaseDaoImpl<T> base;
	@Override
	public T AddAnaUpdate(T t) {
		return base.AddAnaUpdate(t);
	}

	@Override
	public void remove(T t) {
		base.remove(t);
	}

	@Override
	public List<? extends Object> getAll(T t) {
		return base.getAll(t);
	}

	@Override
	public int getCount(T t) {
		return base.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, T t) {
		base.updateById(id, map, t);
	}

	@Override
	public T findById(Object id, T t) {
		return base.findById(id, t);
	}

}
