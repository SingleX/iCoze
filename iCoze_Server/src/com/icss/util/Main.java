package com.icss.util;

import com.icss.dao.jdbcimpl.BaseDao;

public class Main {
	public static void main(String[] args) {
		
		// 初始化用户数据库
		BaseDao baseDao = new BaseDao();
		baseDao.doInit();
		new TCPServer();
	}
}