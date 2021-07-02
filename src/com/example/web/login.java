package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;  

public class login extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
		 String userName=request.getParameter("username");
		 String password=request.getParameter("pass");
		 DB db = (DB) getServletContext().getAttribute("Database");
		 HttpSession session = request.getSession();
		 PrintWriter out = response.getWriter();
		 User user=null;
		 user=db.findUser(userName,password);
		if(user==null){
			request.setAttribute("message","Invalid Credential");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}	
		else{
			session.setAttribute("user",user);
			session.setMaxInactiveInterval(10*60);
			request.getRequestDispatcher("/inbox.jsp").forward(request, response);
		}	
    } 
}