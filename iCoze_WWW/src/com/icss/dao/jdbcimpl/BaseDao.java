package com.icss.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	
	protected Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	protected int result;
	
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加载数据库引擎失败");
		}
	}
	
	/**
	 * 建立数据库连接
	 */
	public void getConn() {
		String url = "jdbc:sqlserver://localhost:1433;dataBaseName=iCoze";
		String user = "sa";
		String password = "123456";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败,请检查服务/数据库名/登陆账户和密码！");
		}
	}

	/**
	 * 关闭所有数据库连接
	 */
	public void closeAll() {
		try {
			if (pstmt != null && !pstmt.isClosed()) {
				pstmt.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库关闭连接异常");
		}
	}

	/**
	 * 查询数据库
	 */
	public void doQuery(String sql, Object... params) {
		try {
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询数据库异常！");
		}
	}

	/**
	 * 操作数据库：增删改
	 */
	public void doOperate(String sql, Object... params) {
		try {
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断表是否存在
	 */
	boolean existTable(String tableName){
		DatabaseMetaData meta;
		try {
			meta = (DatabaseMetaData) conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void doInit() {
		// TODO Auto-generated method stub
		getConn();
		String sql = null;
		
		if (!existTable("Users")) {
			// 初始化表Users
			sql = "create table Users(userId int primary key identity(1,1), userNo nvarchar(10), userNickname nvarchar(10), userPassword nvarchar(20), userSex nvarchar(10), userImage nvarchar(100))";
			doOperate(sql);
			sql = "insert into Users values('10001', '韩雪芹', '123456', '女', '13.jpg')";
			doOperate(sql);
			sql = "insert into Users values('10002', '张良', '123456', '男', '15.jpg')";
			doOperate(sql);
			sql = "insert into Users values('10003', '刘磊', '123456', '男', '12.jpg')";
			doOperate(sql);
			sql = "insert into Users values('10004', '高鑫', '123456', '男', '16.jpg')";
			doOperate(sql);
			sql = "insert into Users values('10005', '李朋伟', '123456', '男', '13.jpg')";
			doOperate(sql);
			sql = "insert into Users values('10006', '刘红军', '123456', '男', '10.jpg')";
			doOperate(sql);
			System.out.println("====初始化表Users成功===");
		}else {
			System.out.println("Users表已存在");
		}
		
		if (!existTable("UFRelations")) {
			// 初始化表UFRelations
			sql = "create table UFRelations(ufrId int primary key identity(1,1), userId int, friendId int, friendRemarks nvarchar(40), friendGroup nvarchar(40))";
			doOperate(sql);
			sql = "insert into UFRelations values(1, 2, '小白', '分组1')";
			doOperate(sql);
			sql = "insert into UFRelations values(1, 3, '小黑', '分组1')";
			doOperate(sql);
			sql = "insert into UFRelations values(2, 1, '小白', '分组2')";
			doOperate(sql);
			sql = "insert into UFRelations values(2, 3, '小黄', '分组2')";
			doOperate(sql);
			System.out.println("====初始化表UFRelations成功===");
		}else {
			System.out.println("UFRelations表已存在");
		}
		
		if (!existTable("Groups")) {
			// 初始化表Groups
			sql = "create table Groups(groupId int primary key identity(1,1), groupNo nvarchar(10), groupName nvarchar(40), groupBroadcast nvarchar(40), groupImage nvarchar(40))";
			doOperate(sql);
			sql = "insert into Groups values('800000', 'iCoze', '这是群广播~~~~', '00.gif')";
			doOperate(sql);
			System.out.println("====初始化表Groups成功===");
		}else {
			System.out.println("Groups表已存在");
		}
		
		if (!existTable("UGRelations")) {
			// 初始化表UGRelations
			sql = "create table UGRelations(ugrId int primary key identity(1,1), userId int, groupId int)";
			doOperate(sql);
			sql = "insert into UGRelations values(1, 1)";
			doOperate(sql);
			sql = "insert into UGRelations values(2, 1)";
			doOperate(sql);
			sql = "insert into UGRelations values(3, 1)";
			doOperate(sql);
			System.out.println("====初始化表UGRelations成功===");
		}else {
			System.out.println("UGRelations表已存在");
		}
		
		if (!existTable("OutlineMsgs")) {
			// 初始化表OutlineMsgs
			sql = "create table OutlineMsgs(oId int primary key identity(1,1), userId int, friendId int, message nvarchar(1000))";
			doOperate(sql);
			sql = "insert into OutlineMsgs values(1, 2, '你好10001，这是我的离线消息')";
			doOperate(sql);
			System.out.println("====初始化表OutlineMsgs成功===");
		}else {
			System.out.println("OutlineMsgs表已存在");
		}
		
		if (!existTable("HistoryMsgs")) {
			// 初始化表HistoryMsgs
			sql = "create table HistoryMsgs(hId int primary key identity(1,1), userId int, friendId int, message nvarchar(1000))";
			doOperate(sql);
			sql = "insert into HistoryMsgs values(1, 2, '你好10001，这是我的历史消息')";
			doOperate(sql);
			System.out.println("====初始化表HistoryMsgs成功===");
		}else {
			System.out.println("HistoryMsgs表已存在");
		}
		
		if (!existTable("Logs")) {
			// 初始化表Logs
			sql = "create table Logs(lId int primary key identity(1,1), userId int, Action nvarchar(40), date date)";
			doOperate(sql);
			System.out.println("====初始化表Logs成功===");
		}else {
			System.out.println("Logs表已存在");
		}
		
		closeAll();
	}
}
