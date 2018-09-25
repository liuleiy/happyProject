package com.happyProject.admin.service;

import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Onlink;
import com.happyProject.admin.model.OrderWeekNumber;

public interface OnlinkService extends BaseDao<Onlink> {
	List<OrderWeekNumber> findByCtime(Integer last, Integer end);
	
}
