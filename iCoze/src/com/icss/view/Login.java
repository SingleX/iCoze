package com.icss.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.omg.CORBA.Request;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {

	private static final String IP = "192.168.40.23";
	private static final int PORT = 9001;
	Scanner in = new Scanner(System.in);
	Socket socket;
	// ---------------输入输出流定义------------
	InputStream input;
	InputStreamReader isreader = null;
	BufferedReader reader = null;
	OutputStream out;
	PrintWriter pw = null;
	String receiveMsg = "";
	String sendMsg = "";
	// ------------------------------------------
	// 声明控件
	JPanel panel;
	JLabel lblusername, lblpassword; // 标签
	JTextField txtQQNum; // 输入文本框
	JPasswordField txtPassword; // 密码框
	JButton btnLogin, btnExit, btnRegist; // 按钮

	// 构造方法
	public Login() {
		this.Init();
	}

	// 设置窗体的方法
	private void Init() {
		// -----------------------优化按键----------------------
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ----------------------------------------------------
		// -----------------添加背景---------------------------
		ImageIcon icon3 = new ImageIcon("src\\images\\login_background.jpg");
		JLabel imgJLabel = new JLabel(icon3);

		this.getLayeredPane().add(imgJLabel, new Integer(Integer.MIN_VALUE));
		imgJLabel.setBounds(0, 0, 400, 300);
		Container me = getContentPane();
		me.setLayout(null);
		((JComponent) me).setOpaque(false);
		// ----------------------------------------------------
		// ----------------- 设置窗体属性-----------------------
		this.setBounds(400, 200, 400, 300);
		this.setVisible(true);
		this.setTitle("欢迎登陆");
		this.setSize(new Dimension(400, 300));
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// -----------------------------------------------------
		// ------------------------设置图片----------------------
		ImageIcon icon = new ImageIcon("src\\images\\login_touxiang.jpg");
		ImageIcon icon1 = new ImageIcon("src\\images\\login_small.gif");
		ImageIcon icon2 = new ImageIcon("src\\images\\QQBtn.png");
		// ------------------------------------------------------
		// -----------------------版面内容------------------------
		JPanel panel = new JPanel();
		panel = (JPanel) getContentPane();
		panel.setLayout(null);

		JLabel imageJLabel = new JLabel(icon);
		imageJLabel.setBounds(10, 110, 100, 100);

		JLabel labQQNum = new JLabel("账    号：", icon1, JLabel.CENTER);
		labQQNum.setBounds(120, 110, 100, 30);
		this.txtQQNum = new JTextField(); // ----账号
		this.txtQQNum.setColumns(15);
		txtQQNum.setBounds(220, 110, 150, 30);

		JLabel labPassword = new JLabel("密    码：", icon1, JLabel.CENTER);
		labPassword.setBounds(120, 150, 100, 30);
		this.txtPassword = new JPasswordField();
		txtPassword.setBounds(220, 150, 150, 30); // ----密码
		this.txtPassword.setEchoChar('★');
		this.txtPassword.setColumns(15);

		JCheckBox passBox = new JCheckBox("记住密码");
		passBox.setBounds(140, 190, 80, 15);
		JCheckBox autoBox = new JCheckBox("自动登录");
		autoBox.setBounds(230, 190, 80, 15);

		this.btnLogin = new JButton("登录", icon2);
		this.btnLogin.addActionListener(this);
		btnLogin.setBounds(70, 220, 100, 40); // ----按键
		this.btnRegist = new JButton("注册", icon2);
		btnRegist.setBounds(220, 220, 100, 40);
		this.btnRegist.addActionListener(this);
		// --------------------------------------------------------
		panel.add(imageJLabel);
		panel.add(labQQNum);
		panel.add(txtQQNum);
		panel.add(labPassword);
		panel.add(txtPassword);
		panel.add(passBox);
		panel.add(autoBox);
		panel.add(btnLogin);
		panel.add(btnRegist);
		// TCP
		// ---------------------socket-----------------
		try {
			socket = new Socket(IP, PORT);

			input = socket.getInputStream();
			isreader = new InputStreamReader(input);
			reader = new BufferedReader(isreader);
			out = socket.getOutputStream();
			pw = new PrintWriter(out, true);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ---------------------------------------------
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 通过if判断获取点击的控件对象
		if (e.getActionCommand() == "登录") {

			// 将获取账号和密码
			String username = this.txtQQNum.getText();
			String password = this.txtPassword.getText();
			String regex = "[1-9][0-9]*";

			// --------------非空判断-----------------
			if (username.length() < 1) {
				JOptionPane.showMessageDialog(this, "账号不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
				// 使控件获取焦点
				this.txtQQNum.requestFocus();
				return;
			}
			if (!username.matches(regex)) {
				// --------------逻辑判断---------------
				JOptionPane.showMessageDialog(this, "账号必须为数字串，请重新输入", "登陆提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (password.length() < 1) {
				JOptionPane.showMessageDialog(this, "密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
				this.txtPassword.requestFocus();
				return;
			}

			// -------上传用户登录账户信息------//
			pw.println("Login");

			pw.println(username);
			pw.flush();
			pw.println(password);
			pw.flush();

			try {
				receiveMsg = reader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (receiveMsg.equals("0")) {
				// 登录失败
				// 弹出对话框
				JOptionPane.showMessageDialog(this, "对不起，账号或密码错误\n请核实……", "对话框标题", JOptionPane.WARNING_MESSAGE);
				// 将账号全选
				this.txtQQNum.requestFocus(); // 1、让控件获取焦点
				this.txtQQNum.selectAll(); // 2、将控件内容全选
				this.txtPassword.setText(""); // 3、将密码置空
				return;
			} else if (receiveMsg.equals("1")) {
				this.dispose();
				pw.println("iCoze");
				pw.println(username);
				new iCoze(username, socket);// 打开主面板
			}
		}
		if (e.getActionCommand() == "注册") {
			
			new Register(socket);

		}
	}
}
