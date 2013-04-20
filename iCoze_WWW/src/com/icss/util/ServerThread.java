package com.icss.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.acl.Permission;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.icss.po.Friend;
import com.icss.po.User;
import com.icss.service.FriendService;
import com.icss.service.UserService;
import com.icss.service.impl.FriendServiceImpl;
import com.icss.service.impl.UserServiceImpl;

public class ServerThread implements Runnable {
	
	public static Map<String, String> toFriendIP = new HashMap<String, String>();
	
	Socket socket;
	int port;

	UserService userService = new UserServiceImpl();
	FriendService friendService = new FriendServiceImpl();

	String userNo;
	String userPassword;
	String str = null;

	InputStream input;
	InputStreamReader isReader = null;
	BufferedReader reader = null;
	OutputStream socketOut;
	PrintWriter pw = null;

	ServerThread(Socket t) {
		socket = t;
		try {
			input = socket.getInputStream();
			isReader = new InputStreamReader(input);
			reader = new BufferedReader(isReader);
			socketOut = socket.getOutputStream();
			pw = new PrintWriter(socketOut, true);

		} catch (IOException e) {
		}
	}

	public void run() {

		try {
			while ((str = reader.readLine()) != null) {
				System.out.println("str="+str);
				if (str.equals("Register")) {
					// 用户注册
					String userNickname = reader.readLine();
					String userPassword = reader.readLine();
					String userSex = reader.readLine();
					int n = userService.addUserByReg(userNickname, userPassword, userSex);
					pw.println(n);
				}

				if (str.equals("Login")) {
					// 用户登录
					userNo = reader.readLine();
					userPassword = reader.readLine();
					if (!userService.isValidateUser(userNo, userPassword)) {
						pw.println("0");// 登录失败
					}else {//登录成功
						pw.println("1");
						toFriendIP.put(userNo, socket.getInetAddress().toString());
					}
				}
				
				if (str.equals("iCoze")) {
					/**
					 * 显示好友列表
					 */
					userNo = reader.readLine();
					User user = userService.findUserByUserNo(userNo);
					String userMessage = "";
					userMessage += user.getUserId()+ ":" +user.getUserNo()+ ":" +user.getUserNickname()+ ":" +user.getUserSex()+ ":" +user.getUserImage()+ ":" +user.getUserStat();
					pw.println(userMessage);//回传登录用户信息
					List<Friend> friends = friendService.findAllFriendsByUserId(user.getUserId());
					Friend friend = new Friend();
					String sum = "";
					Iterator<Friend> iterator = friends.iterator();
					while (iterator.hasNext()) {
						friend = iterator.next();
						String friendStat = (toFriendIP.get(friend.getFriendNo()) == null)? "[离线]" : "[在线]"; 
						String s = friend.getFriendId()+":"+friend.getFriendNo() + ":" + friend.getFriendNickname() +":"+friend.getFriendSex()+":"+friend.getFriendImage()+":"+friendStat+ ";";
						sum += s;
					}
					pw.println(sum);// 返回当前登录用户的好友列表
					while ((str = reader.readLine()) != null) {
						System.out.println("str="+str);
						if (str.equals("Chat")) {
							String friendNo = reader.readLine();// 获取聊天对象
							String friendIP = toFriendIP.get(friendNo);
							if (friendIP == null) {
								//如果好友不在线，返回本人IP
								pw.println("OffLine");
							}else {
								//如果好友在线，返回好友IP
								pw.println(friendIP);
								System.out.println("好友IP是："+friendIP);
							}
						}
						if (str.equals("findUser")) {
							String userNo1;
							userNo1 = reader.readLine(); // userNo1 要查找的好友的QQ号
							System.out.println(userNo1);
							user = new User();
							user = userService.findUserByUserNo(userNo1);
							if (user != null) {
								System.out.println("Success");
								pw.println("查找成功");
								pw.println(user.getUserNickname());
								pw.println(user.getUserNo());
								pw.println(user.getUserSex());
								pw.println(user.getUserImage());
								// 弹出新窗口
								// pw.println(user.getUserImage());
								str = reader.readLine();
								if (str.equals("addFriend")) {
									int n = friendService.addFriendByFriendId(userService.findUserByUserNo(userNo).getUserId(), user.getUserId(), "");
									pw.println("addFriendSuccess");
								}
							} else {
								pw.println("查找失败");
							}
						}
						if (str.equals("findGroup")) {
							userNo = reader.readLine();
							System.out.println(userNo);
						}
						
					}
				}
				
//				if (str.equals("Chat")) {
//					String friendNo = reader.readLine();// 获取聊天对象
//					String friendIP = toFriendIP.get(friendNo);
//					if (friendIP == null) {
//						//如果好友不在线，返回本人IP
//						pw.println("OffLine");
//					}else {
//						//如果好友在线，返回好友IP
//						pw.println(friendIP);
//						System.out.println("好友IP是："+friendIP);
//					}
//				}
	
//
//				// reader.close();
//				// isReader.close();
//				// input.close();
//				// pw.close();
//				// socket.close();
//				// System.out.println("断开连接成功");
			}
		} catch (IOException e) {
			return;
		}

	}
}
