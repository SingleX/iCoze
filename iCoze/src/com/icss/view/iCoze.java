package com.icss.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import com.icss.po.Friend;
import com.icss.po.User;

public class iCoze extends JFrame implements ActionListener {

	User user = null;
	Friend friend = null;
	List<Friend> friends = new ArrayList<Friend>();
	String[][] friendsList;

	// fromFriendMessage用于创建聊天对象与聊天窗口的键值对，避免一个聊天对象打开多个聊天窗口
	public static Map<String, PersonChat> fromFriendMessage = new HashMap<String, PersonChat>();

	Socket socket;

	InputStream input;
	InputStreamReader isReader = null;
	BufferedReader reader = null;
	OutputStream socketOut;
	PrintWriter pw = null;

	private JPanel contentPane;

	// 系统托盘
	private Image sysicon;// 托盘图标
	private TrayIcon trayIcon;
	private SystemTray systemTray;// 系统托盘
	private PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
	private MenuItem show = new MenuItem("主面板");
	private MenuItem exit = new MenuItem("退出");

	/**
	 * Create the frame.
	 */
	public iCoze(String userName, Socket t) {

		socket = t;
		// user.setUserNo(userName);
		try {
			input = socket.getInputStream();
			isReader = new InputStreamReader(input);
			reader = new BufferedReader(isReader);
			socketOut = socket.getOutputStream();
			pw = new PrintWriter(socketOut, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doData();
		doInit(user, friends);
		// UDP线程
		Thread thread = new RecvUDP();
		thread.start();
	}

	public void doData() {
		try {
			String userDataString = reader.readLine();
			String dataString = reader.readLine();

			if (dataString == null) {
				dataString = userDataString;
			} else {
				dataString = userDataString.substring(0, userDataString.length() - 1) + ":[在线];" + dataString;
			}
			String regex = ";";
			String regex2 = ":";
			String[] ssStrings = dataString.split(regex);
			friendsList = new String[ssStrings.length][6];
			for (int i = 0; i < ssStrings.length; i++) {
				friend = new Friend();
				String[] ssStrings2 = ssStrings[i].split(regex2);
				friendsList[i][0] = ssStrings2[0];
				friendsList[i][1] = ssStrings2[1];
				friendsList[i][2] = ssStrings2[2];
				friendsList[i][3] = ssStrings2[3];
				friendsList[i][4] = ssStrings2[4];
				friendsList[i][5] = ssStrings2[5];
				friend.setFriendId(Integer.parseInt(ssStrings2[0]));
				friend.setFriendNo(ssStrings2[1]);
				friend.setFriendNickname(ssStrings2[2]);
				friend.setFriendSex(ssStrings2[3]);
				friend.setFriendImage(ssStrings2[4]);
				friend.setFriendStat(ssStrings2[5]);
				friends.add(friend);
			}
			user = new User();
			String[] userData = userDataString.split(regex2);
			user.setUserId(Integer.parseInt(userData[0]));
			user.setUserNo(userData[1]);
			user.setUserNickname(userData[2]);
			user.setUserSex(userData[3]);
			user.setUserImage(userData[4]);
			user.setUserStat("[在线]");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doInit(final User user, List<Friend> friends) {
		setTitle("iCoze");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(950, 100, 283, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		// contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(null);

		// -------------------------------------
		ImageIcon icon3 = new ImageIcon("src\\images\\iCoze_title.jpg");
		JLabel imgJLabel = new JLabel(icon3);

		this.getLayeredPane().add(imgJLabel, new Integer(Integer.MIN_VALUE));
		imgJLabel.setBounds(0, 0, icon3.getIconWidth(), icon3.getIconHeight());
		// -------------------------------------

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 290, 70);
		panel.setLayout(null);
		contentPane.add(panel);

		// 头像
		ImageIcon icon = new ImageIcon("src\\images\\touxiang\\" + user.getUserImage());
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(10, 15, 41, 41);
		panel.add(lblNewLabel);

		// 在线状态
		// ImageIcon icon2 = new ImageIcon("src\\03.png");
		// JLabel label = new JLabel(icon2);
		// label.setBounds(63, 17, 19, 19);
		// panel.add(label);

		int stat = 0;
		if (user.getUserStat().equals("离线")) {
			stat = 1;
		}
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(63, 17, 58, 19);
		comboBox.addItem("在线");
		comboBox.addItem("离线");
		comboBox.addItem("隐身");
		comboBox.setSelectedIndex(stat);
		// 在线、隐身的事件监听
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(comboBox.getSelectedItem().toString());

			}
		});
		panel.add(comboBox);

		// 昵称
		JLabel label2 = new JLabel();
		label2.setBounds(125, 17, 100, 20);
		label2.setFont(getFont());
		label2.setText(user.getUserNickname());
		panel.add(label2);

		// 个性签名
		JTextField jTextField = new JTextField();
		jTextField.setBounds(63, 42, 195, 20);
		jTextField.setText("原来人心是热的，眼泪是苦的……");
		jTextField.setBorder(null);
		jTextField.setEditable(true);
		jTextField.setAutoscrolls(true);
		panel.add(jTextField);
		panel.add(imgJLabel);
		// 选项卡--主面板
		JPanel panel2 = new JPanel();
		panel2.setBounds(0, 70, 280, 381);
		panel2.setBackground(new Color(241, 201, 149));
		panel2.setLayout(null);
		contentPane.add(panel2);

		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setBounds(0, 0, 280, 381);
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // 设置选项卡的布局方式。滚动
		panel2.add(jTabbedPane);

		// 好友列表

		JPanel panel4 = new JPanel();
		panel4.setBounds(0, 0, 280, 381);
		panel4.setBackground(new Color(241, 201, 149));
		int x = friends.size();
		panel4.setLayout(new GridLayout(1, x));
		jTabbedPane.addTab("好友", null, panel4, "点击查看好友列表");

		Vector<String> vector = new Vector<String>();

		// 数组的声明不可以为空，会影响后面的付值
		// String[] nameStrings = null;
		Icon[] icons = new Icon[x];
		// 为朋友属性赋值
		int i = 0;
		Iterator<Friend> iterator = friends.iterator();
		while (iterator.hasNext()) {
			Friend friend = (Friend) iterator.next();
			vector.add(friend.getFriendStat() + " " + friend.getFriendNickname());
			icons[i] = new ImageIcon("src\\images\\touxiang\\" + friend.getFriendImage());
			i++;
		}
		final JList list = new JList(vector);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)// 双击出现聊天窗口
				{
					// setExtendedState(Frame.NORMAL);
					if (socket != null) {
						pw.println("Chat");
						pw.println(friendsList[list.getSelectedIndex()][1]);// 上传对方的No

						try {
							String friendIP = reader.readLine();
							PersonChat pChat = fromFriendMessage.get(friendsList[list.getSelectedIndex()][2]);
							if (pChat == null) {
								pChat = new PersonChat(user.getUserNickname(), socket.getLocalAddress().toString(), friendsList[list.getSelectedIndex()][2], friendIP);
								fromFriendMessage.put(friendsList[list.getSelectedIndex()][2], pChat);
							}
							// setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		list.setCellRenderer(new ShowPaint(icons));
		// list.setBorder(jTextField.getBorder());
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(getSize());

		panel4.add(scrollPane);

		// 群列表
		JPanel panel5 = new JPanel();
		jTabbedPane.addTab("群列表", null, panel5, "点击查看群列表");

		// 下方的选项设置
		JPanel panel3 = new JPanel();
		panel3.setBounds(0, 448, 280, 55);
		contentPane.add(panel3);

		JButton button = new JButton("系统设置");
		button.addActionListener(this);
		button.setBounds(0, 450, 90, 40);
		panel3.add(button);

		JButton button1 = new JButton("添加分组");
		button1.addActionListener(this);
		button1.setBounds(100, 450, 90, 40);
		panel3.add(button1);

		JButton button2 = new JButton("查找");
		button2.addActionListener(this);
		button2.setBounds(210, 450, 70, 40);
		panel3.add(button2);
		// panel3.getBackground();
		panel3.setBackground(new Color(241, 201, 149));

		// 系统托盘icon初始化
		sysicon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("mini.png"));// 托盘图标显示的图片

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
			trayIcon = new TrayIcon(sysicon, "iCoze", pop);
			// wasw100
			pop.add(show);
			pop.add(exit);

			try {
				systemTray.add(trayIcon); // 设置托盘的图标
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			addWindowListener(new WindowAdapter() {
				public void windowIconified(WindowEvent e) {
					dispose();// 窗口最小化时dispose该窗口
				}
			});

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1 && e.getButton() != MouseEvent.BUTTON3) {// 左击击托盘窗口再现，如果是双击就是e.getClickCount()
						setVisible(true);
						setExtendedState(JFrame.NORMAL);// 设置此 frame 的状态。
					}
				}
			});

			show.addActionListener(new ActionListener() { // 点击"主面板"菜单后将窗口显示出来

				public void actionPerformed(ActionEvent e) {
					// systemTray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					setVisible(true); // 显示窗口
					setExtendedState(JFrame.NORMAL);
				}
			});
			exit.addActionListener(new ActionListener() { // 点击“退出”菜单后推出程序

				public void actionPerformed(ActionEvent e) {
					// 这里可以写执行退出时执行的操作
					System.exit(0); // 退出程序
				}
			});
		}// 托盘图标部分结束

		// 以下是swing程序
		setIconImage(sysicon);// 更改程序图标
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("系统设置")) {

		}
		if (e.getActionCommand().equals("添加分组")) {

		}
		if (e.getActionCommand().equals("查找")) {
			new Search(socket);
		}
	}
}
