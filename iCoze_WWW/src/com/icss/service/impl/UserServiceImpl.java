package com.icss.service.impl;

import com.icss.dao.UserDao;
import com.icss.dao.jdbcimpl.UserDaoImpl;
import com.icss.po.User;
import com.icss.service.UserService;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = new UserDaoImpl();

	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		return userDao.findUserByUserId(userId);
	}

	public User findUserByUserNo(String userNo) {
		// TODO Auto-generated method stub
		return userDao.findUserByUserNo(userNo);
	}

	public boolean isValidateUser(String userNo, String userPassword) {
		// TODO Auto-generated method stub
		return userDao.isValidateUser(userNo, userPassword);
	}

	public int updateUserIP(String userNo, String userIP) {
		// TODO Auto-generated method stub
		return userDao.updateUserIP(userNo, userIP);
	}

	public int addUserByReg(String userNickname, String userPassword, String userSex) {
		// TODO Auto-generated method stub
		return userDao.addUserByReg(userNickname, userPassword, userSex);
	}

	

}
