package com.icss.dao;

/**
 * 用户自己的操作 
 */

import com.icss.po.User;

public interface UserDao {

	// 根据用户Id查找用户
	User findUserByUserId(int userId);

	// 根据用户No查找用户
	User findUserByUserNo(String userNo);

	// 验证用户名密码
	boolean isValidateUser(String userNo, String userPassword);
	
	//更新用户IP
	int updateUserIP(String userNo, String userIP);
	
	//用户注册
	int addUserByReg(String userNickname, String userPassword, String userSex);

}
