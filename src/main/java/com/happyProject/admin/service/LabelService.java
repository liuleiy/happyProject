package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.PageBean;

public interface LabelService extends BaseDao<Label>{
	PageBean<Label> findByDataAndCount(Integer currentPage, Integer pageSize);

}
