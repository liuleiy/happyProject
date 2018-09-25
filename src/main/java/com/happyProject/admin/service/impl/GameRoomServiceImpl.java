package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.GameRoomDaoImpl;
import com.happyProject.admin.model.GameRoom;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.GameRoomService;

@Repository
public class GameRoomServiceImpl implements GameRoomService{

	@Resource
	private GameRoomDaoImpl gameRoomDao;
	
	@Override
	public GameRoom AddAnaUpdate(GameRoom t) {
		return gameRoomDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(GameRoom t) {
		gameRoomDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(GameRoom t) {
		return gameRoomDao.getAll(t);
	}

	@Override
	public int getCount(GameRoom t) {
		return gameRoomDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, GameRoom t) {
		gameRoomDao.updateById(id, map, t);
	}

	@Override
	public PageBean<GameRoom> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer del) {
		return gameRoomDao.findByDataAndCount(name, currentPage, pageSize, startTime, endTime, del);
	}

	@Override
	public GameRoom findById(Object id, GameRoom t) {
		return gameRoomDao.findById(id, t);
	}

}
