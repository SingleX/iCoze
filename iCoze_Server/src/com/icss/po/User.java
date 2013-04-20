package com.icss.po;

public class User {
	
	//用户ID，NO，昵称，密码，性别，头像，状态
	private int userId;
	private String userNo;
	private String userNickname;
	private String userPassword;
	private String userSex;
	private String userImage;
	private String userStat;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getUserStat() {
		return userStat;
	}
	public void setUserStat(String userStat) {
		this.userStat = userStat;
	}
	public User(int userId, String userNo, String userNickname, String userPassword, String userSex, String userImage, String userStat) {
		super();
		this.userId = userId;
		this.userNo = userNo;
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userSex = userSex;
		this.userImage = userImage;
		this.userStat = userStat;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
}
