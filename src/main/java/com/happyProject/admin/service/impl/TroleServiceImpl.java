package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.TroleDaoImpl;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Trole;
import com.happyProject.admin.service.TroleService;

@Repository
public class TroleServiceImpl implements TroleService{
	@Resource
	private TroleDaoImpl troleDao;
	
	@Override
	public Trole AddAnaUpdate(Trole t) {
		return troleDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(Trole t) {
		troleDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Trole t) {
		return troleDao.getAll(t);
	}

	@Override
	public int getCount(Trole t) {
		return troleDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Trole t) {
		troleDao.updateById(id, map, t);
	}

	@Override
	public Trole findById(Object id, Trole t) {
		return troleDao.findById(id, t);
	}

	@Override
	public PageBean<Trole> findByDataAndCount(Integer currentPage, Integer pageSize) {
		return troleDao.findByDataAndCount(currentPage, pageSize);
	}

}
