package com.icss.dao.jdbcimpl;

import java.sql.SQLException;

import com.icss.dao.UserDao;
import com.icss.po.User;

public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		User user = null;
		getConn();
		String sql = "select * from Users where userId = ?";
		doQuery(sql, userId);
		
		try {
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUserNo(rs.getString(2));
				user.setUserNickname(rs.getString(3));
				user.setUserPassword(rs.getString(4));
				user.setUserSex(rs.getString(5));
				user.setUserImage(rs.getString(6));
				user.setUserStat("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeAll();
		return user;
	}
	
	@Override
	public User findUserByUserNo(String userNo) {
		// TODO Auto-generated method stub
		
		User user = null;
		getConn();
		String sql = "select * from Users where userNo = ?";
		doQuery(sql, userNo);
		
		try {
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUserNo(rs.getString(2));
				user.setUserNickname(rs.getString(3));
				user.setUserPassword(rs.getString(4));
				user.setUserSex(rs.getString(5));
				user.setUserImage(rs.getString(6));
				user.setUserStat("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeAll();
		return user;
	}

	@Override
	public boolean isValidateUser(String userNo, String userPassword) {
		// TODO Auto-generated method stub
		User user = findUserByUserNo(userNo);
		if (user != null && user.getUserPassword().equals(userPassword)) {
			return true;
		}
		return false;
	}

	@Override
	public int updateUserIP(String userNo, String userIP) {
		// TODO Auto-generated method stub
		//这个暂时木有写
		return 0;
	}

	@Override
	public int addUserByReg(String userNickname, String userPassword, String userSex) {
		// TODO Auto-generated method stub
		int n = (int) ((Math.random()*100000)+1);
		String userNo = n + "";
		getConn();
		String sql = "insert into Users values(?, ?, ?, ?, '0.gif')";
		doOperate(sql, userNo, userNickname, userPassword, userSex);
		closeAll();
		return n;
	}
}
