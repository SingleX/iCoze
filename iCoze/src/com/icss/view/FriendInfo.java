package com.icss.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class FriendInfo extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Box baseBox,box1,box2,box3,box4,box5,box6,box7;
	
    JTextField txtNickName,txtNoName,txtSexName,txtRemarkName,txtQianName;
	
	public FriendInfo(){
		setBackground(Color.orange);
		this.Init();
	}
	@SuppressWarnings("static-access")
	public void Init(){
		//标题
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Container me = getContentPane();
		me.setBackground(getBackground().pink);
		this.setTitle("某某的资料");              //Title
		this.setSize(new Dimension(450,260));  //设置大小
		this.setLocationRelativeTo(this); //居中
		setLayout(new FlowLayout());
		//各个条目
		JLabel labNickName = new JLabel("昵         称：");
		this.txtNickName = new JTextField();
		txtNickName.setColumns(20);
		txtNickName.setText("liu");
		
		JLabel labSetNo = new JLabel("号        	 码：");
		this.txtNoName = new JTextField();
		txtNoName.setColumns(20);
		txtNoName.setText("123456");
		txtNoName.setEditable(false);
		
		JLabel labSex = new JLabel("性         别：");
		String[] z = {"男","女","保密"};
		JComboBox cboSex = new JComboBox(z);
		
		JLabel labRemark = new JLabel("备         注：");
		this.txtRemarkName = new JTextField();
		txtRemarkName.setColumns(20);
		txtRemarkName.setText("刘");
		
		JLabel labGeQian = new JLabel("个性签名：");
		this.txtQianName = new JTextField();
		txtQianName.setColumns(20);
		txtQianName.setText("http");
 
		String[] y = {"1980","1981","1982","1983","1984","1985","1986",
				"1987","1988","1989","1990","1991","1992","1993","1994"};
		JComboBox cboBirthYear = new JComboBox(y);
		String[] m = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		JComboBox cboBirthMonth = new JComboBox(m);
		String[] d = {"01","02","03","04","05","06","07","08","09","10","11","12",
				"13","14","15","16","17","18","19","20","21","22","23",
				"24","25","26","27","28","29","30","31"};
		JComboBox cboBirthDay = new JComboBox(d);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("a.jpg"));
		JLabel labBirth = new JLabel("生   日：",icon,JLabel.LEFT);
		//按钮	
		JButton reJButton = new JButton("修改");
		reJButton.addActionListener(this);
		JButton clJButton = new JButton("关闭");
		clJButton.addActionListener(this);
		//合并窗体中间部分
		box1 = Box.createVerticalBox();
		box1.add(labNickName);
		box1.add(Box.createVerticalStrut(10));
		box1.add(labSetNo);
		box1.add(Box.createVerticalStrut(10));
		box1.add(labSex);
		box1.add(Box.createVerticalStrut(10));
		box1.add(labBirth);
		box1.add(Box.createVerticalStrut(10));
		box1.add(labGeQian);
		box1.add(Box.createVerticalStrut(10));
		box1.add(labRemark);
		//合并窗体右边部分
		box2 = Box.createVerticalBox();
		box2.add(txtNickName);
		box2.add(Box.createVerticalStrut(5));
		box2.add(txtNoName);
		box2.add(Box.createVerticalStrut(5));
		box2.add(cboSex);
		box2.add(Box.createVerticalStrut(5));
		
		box4 = Box.createHorizontalBox();
		box4.add(cboBirthYear);
		box4.add(Box.createHorizontalStrut(5));
		box4.add(cboBirthMonth);
		box4.add(Box.createHorizontalStrut(5));
		box4.add(cboBirthDay);
		box2.add(box4);
		box2.add(Box.createVerticalStrut(5));
		box2.add(txtQianName);
		box2.add(Box.createVerticalStrut(5));
		box2.add(txtRemarkName);
		
		box5 = Box.createHorizontalBox();
		box5.add(box1);
		box5.add(Box.createHorizontalStrut(10));
		box5.add(box2);
		//合并窗体按钮
		box3 = Box.createHorizontalBox();
		box3.add(reJButton);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(clJButton);
		
		baseBox = Box.createVerticalBox();
		baseBox.add(box5);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(box3);
		//添加图片
		ImageIcon icon1 = new ImageIcon(this.getClass().getResource("b.jpg"));
		JLabel labImg = new JLabel("",icon1,JLabel.LEFT);
		//合并窗体左边部分
		box7 = Box.createVerticalBox();
		box7.add(labImg);
		box7.add(Box.createVerticalStrut(100));
		//合并主窗体
		box6 = Box.createHorizontalBox();
		box6.add(box7);
		box6.add(Box.createHorizontalStrut(10));
		box6.add(baseBox);
		add(box6);
		setVisible(true);
	}
	//添加监听器
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand() == "修改"){
			JOptionPane.showMessageDialog(this, "修改成功！","提示",JOptionPane.WARNING_MESSAGE);
		}
		if(e.getActionCommand()=="关闭"){
			System.exit(0);  //程序自动关闭
		}
	}
	
	public static void main(String args[]){
	    new FriendInfo();
    }
}
