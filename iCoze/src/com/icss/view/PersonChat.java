package com.icss.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.xml.crypto.Data;

public class PersonChat extends JFrame implements ActionListener {

	// 创建一个端口号用于网路通信
	// private static final int PORT = 9528;
	private DatagramSocket sendSocket;
	private DatagramPacket sendPacket;
	String userIP, friendIP;
	InetAddress friendAddress = null;

	/**
	 * 
	 */
	// private SimpleDateFormat sdf = new
	// SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	private static final long serialVersionUID = 1L;
	private JPanel jpanel, p1, p2, p4, p5;
	private JScrollPane JSPanelContext, JSPanelSend;
	private JSplitPane jSPlit1, jSPlit2;
	private JLabel lblInfo, lblInfo1, lblInfo2;
	private JTextArea txtContent, txtSend;
	private JButton btnSend;
	private JButton btnClose;
	private JComboBox word;
	private JComboBox size;
	private JComboBox color;
	private JComboBox form;
	
	private int ss = Font.PLAIN;
	private int DX = 12; 
	private String str = "宋体"; 
	private Color col =  Color.BLACK;

	public String userNickname, friendNickname;

	public PersonChat(String userNickname, String userIP, String friendNickName, String friendIP) {
		this.userNickname = userNickname;
		this.friendNickname = friendNickName;
		final String friend  = friendNickname;

		// 标题
		this.setTitle("与 " + this.friendNickname + " 亲密聊天");
		this.setSize(new Dimension(550, 510));
		this.setLocationRelativeTo(this);

		// 关闭窗口
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				iCoze.fromFriendMessage.remove(friend);
			}
		});
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setResizable(false);
		// 主体开始
		this.jpanel = (JPanel) this.getContentPane();
		this.jpanel.setLayout(new BorderLayout());
		// 标签
		ImageIcon icon = new ImageIcon("src\\images\\chat_tixing.jpg");
		this.lblInfo = new JLabel("亲！交谈中请勿轻信汇款、中奖信息、陌生电话，勿使用外挂软件", icon, JLabel.LEFT);
		this.txtContent = new JTextArea(7, 35);
		this.txtSend = new JTextArea();
		this.txtSend.setLineWrap(true);
		this.txtContent.setLineWrap(true);
		this.txtContent.setEditable(true);// gaoxin.add
		this.btnSend = new JButton("发送");
		btnSend.addActionListener(this);
		this.btnClose = new JButton("关闭");
		btnClose.addActionListener(this);
		// 文本框
		this.JSPanelSend = new JScrollPane();
		this.JSPanelSend.getViewport().add(this.txtSend);
		this.JSPanelContext = new JScrollPane();
		this.JSPanelContext.getViewport().add(this.txtContent);
		ImageIcon icon1 = new ImageIcon("src\\images\\chat_show1.jpg");
		this.lblInfo1 = new JLabel("", icon1, JLabel.CENTER);
		ImageIcon icon2 = new ImageIcon("src\\images\\chat_show2.jpg");
		this.lblInfo2 = new JLabel("", icon2, JLabel.CENTER);
		this.p1 = new JPanel();
		this.p1.setLayout(new BorderLayout());
		this.p1.add(this.JSPanelContext, BorderLayout.WEST);
		this.p1.add(this.lblInfo1, BorderLayout.EAST);
		this.p2 = new JPanel();
		this.p2.setLayout(new BorderLayout());
		this.p2.add(this.JSPanelSend, BorderLayout.CENTER);
		this.p2.add(this.lblInfo2, BorderLayout.EAST);
		// 下拉框
		this.p5 = new JPanel();
		this.word = new JComboBox();
		word.addItem("宋体");
		word.addItem("楷体");
		word.addItem("隶书");
		word.setSelectedIndex(0);
		this.size = new JComboBox();
		size.addItem("12");
		size.addItem("24");
		size.addItem("36");
		size.addItem("48");
		size.setSelectedIndex(0);
		this.color = new JComboBox();
		color.addItem("黑色");
		color.addItem("红色");
		color.addItem("绿色");
		color.addItem("黄色");
		color.setSelectedIndex(0);
		this.form = new JComboBox();
		form.addItem("常规");
		form.addItem("粗体");
		form.addItem("斜体");
		form.setSelectedIndex(0);
		this.p5.add(this.word);
		this.p5.add(this.form);
		this.p5.add(this.size);
		this.p5.add(this.color);
		
		//设置字体样式
		
		this.word.addItemListener(new java.awt.event.ItemListener() { 
			public void itemStateChanged(java.awt.event.ItemEvent e) { 
				if(e.getItem().toString()=="宋体") 
					str="宋体"; 
				if(e.getItem().toString()=="隶书") 
					str="隶书"; 
				if(e.getItem().toString()=="楷体") 
					str="楷体"; 
				txtSend.setFont(new Font(str,ss,DX)); 
			} 
		});   
		
		this.form.addItemListener(new java.awt.event.ItemListener() { 
			public void itemStateChanged(java.awt.event.ItemEvent e) { 
				if(e.getItem().toString()=="常规") 
					ss=Font.PLAIN; 
				if(e.getItem().toString()=="粗体") 
					ss=Font.BOLD; 
				if(e.getItem().toString()=="斜体") 
					ss=Font.ITALIC;
				txtSend.setFont(new Font(str,ss,DX)); 
			} 
		}); 

		this.size.addItemListener(new java.awt.event.ItemListener() { 
			public void itemStateChanged(java.awt.event.ItemEvent e) { 
				if(e.getItem().toString()=="12") 
					DX=12; 
				if(e.getItem().toString()=="24") 
					DX=24; 
				if(e.getItem().toString()=="36") 
					DX=36; 
				if(e.getItem().toString()=="48") 
					DX=48; 
				txtSend.setFont(new Font(str,ss,DX));  
			} 
		}); 
 
		this.color.addItemListener(new java.awt.event.ItemListener() { 
			public void itemStateChanged(java.awt.event.ItemEvent e) { 
				if(e.getItem().toString()=="黑色") 
					col=Color.BLACK; 
				if(e.getItem().toString()=="红色") 
					col=Color.RED; 
				if(e.getItem().toString()=="绿色") 
					col=Color.GREEN; 
				if(e.getItem().toString()=="黄色") 
					col=Color.YELLOW; 
				txtSend.setForeground(col); 
			} 
		});  
		
		// 合并
		this.jSPlit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.p5, this.p2);
		this.jSPlit2.setOneTouchExpandable(true);
		this.jSPlit2.setResizeWeight(0.3);
		this.jSPlit2.setEnabled(false);
		this.jSPlit2.setOneTouchExpandable(false);
		this.jSPlit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.lblInfo, this.p1);
		this.jSPlit1.setOneTouchExpandable(true);
		this.jSPlit1.setResizeWeight(0.1);
		this.jSPlit1.setEnabled(false);
		this.jSPlit1.setOneTouchExpandable(false);
		// 按钮
		this.p4 = new JPanel();
		this.p4.setLayout(new FlowLayout());
		this.p4.add(this.btnSend);
		this.p4.add(this.btnClose);
		// 主体结尾
		this.jpanel.add(this.jSPlit1, BorderLayout.NORTH);
		this.jpanel.add(this.jSPlit2, BorderLayout.CENTER);
		this.jpanel.add(this.p4, BorderLayout.SOUTH);
		this.setVisible(true);
		
		this.userIP = userIP.substring(1, userIP.length());
		if (friendIP.equals("OffLine")) {
			this.txtContent.append("系统提示：该好友不在线，您的消息将无法发送给对方！\n");
			this.friendIP = "0.0.0.0";
		}else {
			this.friendIP = friendIP.substring(1, friendIP.length());
		}
		
//		Thread thread = new Thread(this);
//		thread.start();
	}

	// 发送
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "关闭") {
			this.dispose();
		}
		if (e.getActionCommand() == "发送") {
			String news =  txtSend.getText().trim();
			String news2 = userNickname + "#" + userIP + "#" + friendNickname + "#" + friendIP + "#" + news;
			byte[] sendData = news2.getBytes();
			try {
				friendAddress = InetAddress.getByName(this.friendIP);
				sendPacket = new DatagramPacket(sendData, sendData.length, friendAddress, 8000);
				sendSocket = new DatagramSocket();
				sendSocket.send(sendPacket);
				txtContent.append(userNickname + getDate() + "\n    " + news + "\n");
			} catch (Exception e1) {
				// TODO: handle exception
			}
			txtSend.setText("");
		}
	}

	public void addMessage(String message){
		
		txtContent.append(friendNickname+getDate() + "\n    " + message + "\n");
	}

	public String getDate() {
		Date date = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("  (yyyy-MM-dd HH:mm:ss)");
		String dateString = matter.format(date);
		return dateString;

	}
}
