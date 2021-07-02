package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;  
import org.bson.types.ObjectId;

public class ForgotPassword extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	     DB db = (DB) getServletContext().getAttribute("Database");
		 String userName=request.getParameter("userName");
		 String phoneNo=request.getParameter("phone");
		 String password=request.getParameter("password");
	     int result=db.setPassword(userName,phoneNo,password);
		 if(result==1)
		 {
			request.setAttribute("message","Password Changed");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		 }
         else
		 {
			request.setAttribute("message","Details don't match!!");
			request.getRequestDispatcher("/ForgotPassword.jsp").forward(request, response);
		 }			 
    }
} 