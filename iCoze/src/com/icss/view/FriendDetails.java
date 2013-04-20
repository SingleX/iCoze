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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FriendDetails extends JFrame implements ActionListener {
	
	Socket socket;
	
	InputStream input;
	InputStreamReader isreader = null;
	BufferedReader reader = null;
	OutputStream out;
	PrintWriter pw = null;
	
	JButton addFriengButton;
	
	public FriendDetails(Socket s,String userNickName,String userSex,String userNum, String userImage){
		
		socket = s;
		
		try {
			input = socket.getInputStream();
			isreader = new InputStreamReader(input);
			reader = new BufferedReader(isreader);
			out = socket.getOutputStream();
			pw = new PrintWriter(out, true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	this.setTitle("好友"+userNickName+"的详细信息：");
    	this.setLocationRelativeTo(this);
    	this.setSize(new Dimension(500,300));
    	this.setLayout(new FlowLayout());
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setVisible(true);
    	//                设置背景图片
    	ImageIcon icon3 = new ImageIcon("src\\images\\FriendDetailsBg.jpg");
    	JLabel imgJLabel = new JLabel(icon3);
    	
    	this.getLayeredPane().add(imgJLabel,new Integer(Integer.MIN_VALUE));
    	imgJLabel.setBounds(0,0,500,300);
    	Container me = getContentPane();
    	me.setLayout(null);
    	((JComponent) me).setOpaque(false);
    	
    	JPanel panel = new JPanel();
    	panel = (JPanel) getContentPane();
    	panel.setLayout(null);
    	
    	ImageIcon icon = new ImageIcon("src\\images\\touxiang\\"+ userImage);
    	JLabel labImage = new JLabel(icon);
    	labImage.setBounds(40, 100, 41, 41);
    	
    	JLabel labNickName = new JLabel(userNickName);
    	labNickName.setBounds(40, 160,50, 20);
    	JLabel labQQNum = new JLabel(userNum);
    	labQQNum.setBounds(40, 180,50, 20);
    	JLabel labSex = new JLabel(userSex);
    	labSex.setBounds(40, 200,50, 20);
    	
    	ImageIcon icon1 = new ImageIcon("src\\images\\add.jpg");
    	this.addFriengButton = new JButton("添加好友",icon1);
    	addFriengButton.setBounds(240, 100, 110, 40);
    	addFriengButton.addActionListener(this);
    	
        panel.add(labImage);
    	panel.add(labQQNum);
    	panel.add(labNickName);
    	panel.add(labSex);
    	panel.add(addFriengButton);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand() == "添加好友"){
			pw.println("addFriend");
			try {
				
				String string = reader.readLine();
				
				if(string.equals("addFriendSuccess")){
					JOptionPane.showMessageDialog(this, "添加成功！","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
