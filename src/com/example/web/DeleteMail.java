package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.bson.types.ObjectId;

public class DeleteMail extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	     DB db = (DB) getServletContext().getAttribute("Database");
		 HttpSession session =request.getSession(false);
		 User user=(User)session.getAttribute("user");
		 List<String> mailIdListInString = Arrays.asList(request.getHeader("mailIdList").split(","));
				 for(String mailId:mailIdListInString)
				 {
				   db.moveToTrash(mailId,user.getUserName());
				 }
    }
}