package com.icss.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icss.po.User;
import com.icss.service.UserService;
import com.icss.service.impl.UserServiceImpl;

public class Reg extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Reg() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userNickname = request.getParameter("username");
		String userPassword = request.getParameter("password");
		String userPassword2 = request.getParameter("password2");
		String usergender = request.getParameter("gender");
		
		usergender = usergender.equals("0")? "ÄÐ" : "Å®";

		UserService userService = new UserServiceImpl();		
		int n = userService.addUserByReg(userNickname, userPassword, usergender);
		String num = n+"";
		HttpSession session = request.getSession();
		session.setAttribute("NUMBER", num);
		RequestDispatcher rdp = request.getRequestDispatcher("index.jsp");
		rdp.forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
