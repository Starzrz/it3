package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BL.blservice.UserBL;
import BL.userBL.UserBLImpl;
import server.others.LoginResult;

public class userController extends HttpServlet{
	final String userName = "userName";
	final String passWord = "passWord";
	final String loginPath = "/login.jsp";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("test/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		String path = req.getContextPath();
		String user = req.getParameter(userName);
		String pass = req.getParameter(passWord);
		UserBL bl = new UserBLImpl();
		LoginResult result = bl.getLoginResult(user, pass);
		//测试
		out.write(user+";"+pass);
		resp.sendRedirect(path+loginPath);
		out.flush();
		out.close();
	}
}
