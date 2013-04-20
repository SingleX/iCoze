package com.icss.service.impl;

import java.util.List;

import com.icss.dao.FriendDao;
import com.icss.dao.jdbcimpl.FriendDaoImpl;
import com.icss.po.Friend;
import com.icss.service.FriendService;

public class FriendServiceImpl implements FriendService {
	
	FriendDao friendDao = new FriendDaoImpl();

	public int addFriendByFriendId(int userId, int friendId, String friendGroup) {
		// TODO Auto-generated method stub
		return friendDao.addFriendByFriendId(userId, friendId, friendGroup);
	}

	public int deleteFriendByFriendId(int userId, int friendId) {
		// TODO Auto-generated method stub
		return friendDao.deleteFriendByFriendId(userId, friendId);
	}

	public int modifyFriendByFriendId(int userId, int friendId, String friendGroup) {
		// TODO Auto-generated method stub
		return friendDao.modifyFriendByFriendId(userId, friendId, friendGroup);
	}

	public List<Friend> findAllFriendsByUserId(int userId) {
		// TODO Auto-generated method stub
		return friendDao.findAllFriendsByUserId(userId);
	}

	public Friend findFriendByFriendNo(String friendNo) {
		// TODO Auto-generated method stub
		return friendDao.findFriendByFriendNo(friendNo);
	}

}
