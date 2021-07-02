package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class signup extends HttpServlet{
			public void doPost(HttpServletRequest request,HttpServletResponse response)
			 throws IOException, ServletException {
			 
			 //get Parameters
			 
					 String firstName=request.getParameter("first_name");
					 String lastName=request.getParameter("last_name");
					 String dob=request.getParameter("birthday");
					 String gender=request.getParameter("gender");
					 String userName=request.getParameter("userName");
					 String phone=request.getParameter("phone");
					 String password=request.getParameter("password");
					 
					 //create user 
					     User user=new User();
						user.setFirstName(firstName);
						user.setLastName(lastName);
						user.setGender(gender);
						user.setDob(dob);
						user.setUserName(userName);
						user.setPassword(password);
						user.setPhone(phone);
						showMessage(InsertIntoDatabase(user),response);
						
					 
                       						 
					  
			}
			  //Preparing database for user
			  
			  String InsertIntoDatabase(User user)
			  {
				    DB db=(DB)getServletContext().getAttribute("Database");
					 
					 String message;
					 
					 //enter user in database and create inbox
					 switch(db.createUser(user))
					 {
					   case 1: message="Successfully Signed up!!!";					
							   
							   break;
							   
							   
					   case 2: message="Username is Already taken";
							   break;
                        
					   default:message ="cannot resolve issue";
					          break;
							   
					 }
                     return message;					 
			   }

            void showMessage(String message,HttpServletResponse response)
			{
				String message1="<h1>Intranet Mailing System</h1><br>"+message;
				try{
				   PrintWriter out=response.getWriter();
				   out.println(message1);
				}
				catch (Exception e){
				}
		    }	
			
}