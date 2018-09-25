package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.PageBean;

public interface LabelDao extends BaseDao<Label>{
	PageBean<Label> findByDataAndCount(Integer currentPage, Integer pageSize);
}
