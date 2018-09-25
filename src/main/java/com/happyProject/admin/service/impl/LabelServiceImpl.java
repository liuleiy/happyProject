package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.LabelDaoImpl;
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.LabelService;

@Repository
public class LabelServiceImpl implements LabelService{
	@Resource
	private LabelDaoImpl labelDaoImpl;
	
	@Override
	public Label AddAnaUpdate(Label t) {
		return labelDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(Label t) {
		labelDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Label t) {
		return labelDaoImpl.getAll(t);
	}

	@Override
	public int getCount(Label t) {
		return labelDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Label t) {
		labelDaoImpl.updateById(id, map, t);
	}

	@Override
	public Label findById(Object id, Label t) {
		return labelDaoImpl.findById(id, t);
	}

	@Override
	public PageBean<Label> findByDataAndCount(Integer currentPage, Integer pageSize) {
		return labelDaoImpl.findByDataAndCount(currentPage, pageSize);
	}

}
