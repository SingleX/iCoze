package com.icss.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.icss.dao.jdbcimpl.BaseDao;

public class TCPServer {

	private final static int PORT = 9001;
	private final static int MAX_THREAD = 10;
	private ServerSocket server;
	private Socket socket;
	public static Set<Socket> clients = new HashSet<Socket>();
	public static boolean flag = false;
	public static List<String> message = new ArrayList<String>();
	private ServerThread serverThread = null;

	public TCPServer() {

		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREAD);

		while (true) {
			try {
				server = new ServerSocket(PORT);
				System.out.println("...Waiting...");
			} catch (IOException e) {
			}
			try {
				socket = server.accept();
				System.out.println("上线客户端IP：" + socket.getInetAddress());
				clients.add(socket);
				serverThread = new ServerThread(socket);
				threadPool.execute(serverThread);
				
			} catch (IOException e) {
			}
//
//			if (socket != null) {
//				new ServerThread(socket).start();// 为每个客户端启动一个线程
//			}
		}
	}
}
