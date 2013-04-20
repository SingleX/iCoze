package com.icss.dao.jdbcimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.icss.dao.FriendDao;
import com.icss.po.Friend;

public class FriendDaoImpl extends BaseDao implements FriendDao {

	@Override
	public int addFriendByFriendId(int userId, int friendId, String friendGroup) {
		// TODO Auto-generated method stub
		getConn();
		String sql = "insert into UFRelations values(?, ?, null, ?)";
		doOperate(sql, userId, friendId, friendGroup);
		closeAll();
		return 0;
	}

	@Override
	public int deleteFriendByFriendId(int userId, int friendId) {
		// TODO Auto-generated method stub
		getConn();
		String sql = "detete from UFRelations where userId = ? and friendId = ?";
		doOperate(sql, userId, friendId);
		closeAll();
		return 0;
	}

	@Override
	public int modifyFriendByFriendId(int userId, int friendId, String friendGroup) {
		// TODO Auto-generated method stub
		getConn();
		String sql = "update UFRelations set friendGroup = '?'";
		doOperate(sql, friendGroup);
		closeAll();
		return 0;
	}

	@SuppressWarnings("null")
	@Override
	public List<Friend> findAllFriendsByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Friend> friends = new ArrayList<Friend>();
		Friend friend = null;
		getConn();
		String sql = "select count(friendId) as num from UFRelations where userId = ?";
		doQuery(sql, userId);
		try {
			int count = 0;
			while (rs.next()) {
				count = rs.getInt("num");
			}
			int[] friendId = new int[count];
			sql = "select friendId from UFRelations where userId = ?";
			doQuery(sql, userId);
			int y = 0;
			while(rs.next()){
				friendId[y] = rs.getInt(1);
				y++;
			}
			
			for(int k = 0; k < count; k++){
				sql = "select * from Users where userId = ?";
				doQuery(sql, friendId[k]);
				if (rs.next()) {
					friend = new Friend();
					friend.setFriendId(friendId[k]);
					friend.setFriendNo(rs.getString(2));
					friend.setFriendNickname(rs.getString(3));
					friend.setFriendSex(rs.getString(5));
					friend.setFriendImage(rs.getString(6));
					friends.add(friend);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		closeAll();
		return friends;
	}

	@Override
	public Friend findFriendByFriendNo(String friendNo) {
		// TODO Auto-generated method stub
		Friend friend = new Friend();
		getConn();
		String sql = "select * from Users where friendNo = ?";
		doQuery(sql, friendNo);
		try {
			if (rs.next()) {

				friend.setFriendId(rs.getInt(1));
				friend.setFriendNo(rs.getString(2));
				friend.setFriendNickname(rs.getString(3));
				friend.setFriendSex(rs.getString(5));
				friend.setFriendImage(rs.getString(6));
				friend.setFriendStat("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAll();
		return friend;
	}

}
