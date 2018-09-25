package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.CreateRoomRecord;
import com.happyProject.admin.model.PageBean;

public interface CreateRoomRecordDao extends BaseDao<CreateRoomRecord> {
	
	PageBean<CreateRoomRecord> findByDataAndCount(String code, Integer currentPage, Integer pageSize);

}
