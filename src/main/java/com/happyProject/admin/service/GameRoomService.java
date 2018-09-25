package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.GameRoom;
import com.happyProject.admin.model.PageBean;

public interface GameRoomService extends BaseDao<GameRoom>{
	PageBean<GameRoom> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer del);

}
