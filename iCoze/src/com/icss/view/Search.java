package com.icss.view;

/**
 * 搜索好友功能
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Search extends JFrame implements ActionListener{
	Socket socket;
	
	InputStream input;
	InputStreamReader isreader = null;
	BufferedReader reader = null;
	OutputStream out;
	PrintWriter pw = null;
	JTextField text1,text2;
	
	public Search(Socket s){
		socket = s;
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
			input = socket.getInputStream();
			isreader = new InputStreamReader(input);
			reader = new BufferedReader(isreader);
			out = socket.getOutputStream();
			pw = new PrintWriter(out, true);
			
			
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		super();
		setLayout(null);
		setTitle("查找好友/群");
		//setSize(600,450);
		setBounds(100, 100, 600,450 );
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//------------------------------------------------------------------
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);//定义一个选项卡形式的Panel
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);//设置选项卡的形式
		
		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int selectedIndex = tabbedPane.getSelectedIndex();//获得选中的选项卡索引
				String title = tabbedPane.getTitleAt(selectedIndex);//获得选项卡标签
			}
		});

		//-------------------------------选项卡1
		add(tabbedPane);		
		tabbedPane.setBounds(0, 0, 600,450 );
		JPanel tabPanel1 = new JPanel();//定义面板jPanel1
		//-----------------------set background----------------------------------------
	  	ImageIcon icon3 = new ImageIcon("src\\images\\search_qun.jpg");
	  	ImageIcon icon4 = new ImageIcon("src\\images\\search_friend.jpg");
    	JLabel imgJLabel = new JLabel(icon3);
    	JLabel imgJLabel1 = new JLabel(icon4);
    	
    	this.getLayeredPane().add(imgJLabel,new Integer(Integer.MIN_VALUE));
    	this.getLayeredPane().add(imgJLabel1,new Integer(Integer.MIN_VALUE));
    	imgJLabel.setBounds(0,0,600,450);
    	imgJLabel1.setBounds(0,0,600,450);
		
        //---------------------------------------------------------------
		
		tabPanel1.setLayout(null);
//		tabPanel1 = (JPanel) getContentPane();
		tabbedPane.addTab("查    找    好    友", tabPanel1);

//		tabbedPane.setBackground(Color.getHSBColor(50, 421, 6));
		
		JLabel label1 = new JLabel();//标签
		label1.setBounds(100, 40, 200, 50);
//		label1.setBorder(null);
		label1.setFont(new Font("宋体",Font.BOLD,16));
		label1.setText("请输入好友账号");
		
//		JLabel label11 = new JLabel();//标签11
//		label11.setBounds(100, 150, 200, 50);
//		label1.setBorder(null);
//		label11.setFont(new Font("宋体",Font.BOLD,16));
//		label11.setText("请输入好友昵称");
		
		this.text1 = new JTextField(15);//文本框
		text1.setBounds(100, 90, 180, 30);
		
//		JTextField text11 = new JTextField(15);//文本框11
//		text11.setBounds(100, 200, 180, 30);
		JButton bton1 = new JButton("查找好友");//查询按钮
	    bton1.addActionListener(this);
		bton1.setBounds(300, 90, 100, 30);
		
//		JButton bton11 = new JButton("查询");//查询按钮11
//		bton11.setBounds(300, 200, 60, 30);
		
		
		tabPanel1.setBackground(Color.getHSBColor(50, 421, 6));
//		tabPanel1.setSize(200, 400);
		tabPanel1.add(label1);
//		tabPanel1.add(label11);
		tabPanel1.add(text1);
//		tabPanel1.add(text11);
		tabPanel1.add(bton1);
//		tabPanel1.add(bton11);
		tabPanel1.add(imgJLabel1);

		
		
		//选项卡2
		final JPanel tabPanel2 = new JPanel();
		tabPanel2.setLayout(null);
		tabbedPane.addTab("查      找     群", tabPanel2);
//		tabbedPane.setBounds(20, 20, 100, 80);
		tabbedPane.setBackground(Color.cyan);
		
		JLabel label2 = new JLabel();//标签
		label2.setBounds(100, 40, 200, 50);
//		label1.setBorder(null);
		label2.setFont(new Font("宋体",Font.BOLD,16));
		label2.setText("请输入群账号");
		this.text2 = new JTextField(15);//文本框
		text2.setBounds(100, 100, 180, 30);
		JButton bton2 = new JButton("查找群");//查询按钮
		bton2.addActionListener(this);
		bton2.setBounds(300, 100, 100, 30);
		
		tabPanel2.setBackground(Color.getHSBColor(450, 422, 6));
		tabPanel2.add(label2);
		tabPanel2.add(text2);
		tabPanel2.add(bton2);
		tabPanel2.add(imgJLabel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "查找好友") {
			pw.println("findUser");
//			String userNo= ;
			pw.println(this.text1.getText());
//			new FriendDetails();
			String resultString;
			try {
				resultString = reader.readLine();
				if(resultString.equals("查找成功")){
					String userNickName = reader.readLine();
					String userNum = reader.readLine();
					String userSex = reader.readLine();
					String userImage = reader.readLine();
					new FriendDetails(socket,userNickName,userNum,userSex,userImage);
				}else if(resultString.equals("查找失败")){
					JOptionPane.showMessageDialog(this, "您要查找的用户不存在！"," 查找结果：", JOptionPane.WARNING_MESSAGE);
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
		}
		if (e.getActionCommand() == "查找群") {
			pw.println("findGroup");
		}
	}
}
