package com.icss.view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RecvUDP extends Thread {
	
	private DatagramSocket sendSocket, recvSocket;
	private DatagramPacket sendPacket, recvPacket;
	private String recvMessage = "";

	@Override
	public void run() {
		byte buf[] = new byte[1024]; // 创建一个字节数组，用于接收字节流数据
		try {
			recvPacket = new DatagramPacket(buf, buf.length); // 接受数据
			recvSocket = new DatagramSocket(8000);
		} catch (SocketException e1) {
		}
		while (true) {
			if (recvSocket == null) {
				break;
			} else {
				try {
					recvSocket.receive(recvPacket);
					String recvMsg = new String(recvPacket.getData(), 0, recvPacket.getLength()).trim();
					System.out.println("MSG="+recvMsg);
					String regex = "#";
					String[] strings = recvMsg.split(regex);
					
					recvMessage = recvMsg.substring(strings[0].length()+strings[1].length()+strings[2].length()+strings[3].length()+4, recvMsg.length());
					
					
					if(recvMessage != ""){
						
						PersonChat pChat = iCoze.fromFriendMessage.get(strings[0]);
						if(pChat == null ){
							pChat = new PersonChat(strings[2], strings[3], strings[0], strings[1]);
							iCoze.fromFriendMessage.put(strings[0], pChat);
							pChat.addMessage(recvMessage);
							recvMessage = "";
							
						}
						else {
							pChat.addMessage(recvMessage);
							recvMessage = "";
						}
					}
//					txtContent.append(friendNickname + getDate() + "\n    " + recvMsg + "\n");
				} catch (IOException e) {
				}
			}
		}
	}
	
	
}
