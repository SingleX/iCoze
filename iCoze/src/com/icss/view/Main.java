package com.icss.view;

/**
 * 主程序
 */
import javax.swing.SwingUtilities;

public class Main {

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 思考：1、不规则窗体 2、换肤使用(第三方控件)
		// 使窗体透明
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Login frm = new Login();
				frm.setVisible(true);
			}
		});
	}
}
