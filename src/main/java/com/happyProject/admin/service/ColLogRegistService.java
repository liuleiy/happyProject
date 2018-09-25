package com.happyProject.admin.service;

import java.util.Date;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.ColLogRegist;
import com.happyProject.admin.model.OrderWeekNumber;

public interface ColLogRegistService extends BaseDao<ColLogRegist>{
	OrderWeekNumber getOrderNumber(Date time, Integer start, Integer last);
}
