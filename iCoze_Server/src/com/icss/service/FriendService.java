package com.icss.service;

import java.util.List;

import com.icss.po.Friend;

public interface FriendService {
	// 添加好友
	int addFriendByFriendId(int userId, int friendId, String friendGroup);

	// 删除好友
	int deleteFriendByFriendId(int userId, int friendId);

	// 修改好友昵称
	int modifyFriendByFriendId(int userId, int friendId, String friendGroup);

	// 查看所有好友
	List<Friend> findAllFriendsByUserId(int userId);

	// 查找好友
	Friend findFriendByFriendNo(String friendNo);
}
