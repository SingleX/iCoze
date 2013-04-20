package com.icss.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.icss.po.Friend;
import com.icss.po.User;
import com.icss.service.FriendService;
import com.icss.service.UserService;
import com.icss.service.impl.FriendServiceImpl;
import com.icss.service.impl.UserServiceImpl;

public class UDPServer extends Thread{

	// 创建一个端口号用于网路通信
	private static final int PORT = 9528;
	// 声明一个DatagramSocekt对象
	private DatagramSocket sendSocket,recvSocket;
	// 声明一个DatagramPacket对象
	private DatagramPacket sendPacket,recvPacket;
	
	private String userNo, friendNo;

	public UDPServer(String userNo, String friendNo) {
		this.userNo = userNo;
		this.friendNo = friendNo;
	}
	public void run(){
		try {
			// 步骤1：实例化DatagramSocekt对象
			recvSocket = new DatagramSocket(9527);
//			System.out.println("准备开始接受数据……");
			while (true) {
				
				// 步骤2：实例化datagramPacket对象
				byte[] buf = new byte[128]; // 创建一个字节数组，用于接收字节流数据
				recvPacket = new DatagramPacket(buf, buf.length); // 接受数据
				
				// 步骤3：使用DatagramSocekt对象的receive()方法打开服务器端的接受监听
				recvSocket.receive(recvPacket);
				
				// 步骤4：将数据打印显示在控制台
				String str = new String(recvPacket.getData(),"utf-8");
							
				String message = str.trim();
				System.out.println(userNo+"说"+message);
				
//				String regex = ":";
//				String[] strings = message.split(regex);
//				friendNo = strings[0];
//				
//				//查找好友的IP信息
//				FriendService friendService = new FriendServiceImpl();
//				Friend friend = new Friend();
//				
//				friend = friendService.findFriendByFriendNo(friendNo);
//				friend.setFriendStat("在线");
//				
//				if (friend.getFriendStat().equals("离线")) {
//					String sysMessage = "#系统提示：该好友不在线！";
//					byte[] sendData = sysMessage.getBytes("utf-8");
//					datagramPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(datagramSocket.getInetAddress().toString()), 9528);
//					// 步骤3：发送数据
//					datagramSocket.send(datagramPacket);
//					return;
//				}
				
				sendSocket = new DatagramSocket();
				message = (userNo + "> " + message);
				byte[] sendData = message.getBytes("utf-8");
				sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.40.22"), 9528);
				// 步骤3：发送数据
				sendSocket.send(sendPacket);
				System.out.println("发送成功");
			}
		} catch (SocketException e) {
		} catch (IOException e) {
		}
	}
}
