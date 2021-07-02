package com.example.web;

import com.example.model.*;
import java.io.*;
import java.io.InputStream;
import java.util.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.bson.types.ObjectId;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import org.apache.commons.fileupload.FileItem;


@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class MakeMail extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
		
		 PrintWriter out = response.getWriter();
		 DB db=(DB)getServletContext().getAttribute("Database");
		 HttpSession session = request.getSession(false);
         List<ObjectId> fileIdList=new ArrayList<>();		
		 String subject = request.getParameter("subject");
         String message = request.getParameter("message");
		 String receiverUserName=request.getParameter("to");
		 List<String> fileIdListInString = Arrays.asList(request.getParameter("fileidList").split(","));

		User receiver=null;
		
		if(session==null)
			out.println("can't initialise session");
		else{ 	
			    User user=(User)session.getAttribute("user");
				if(user!=null )
				{
					String senderUserName = user.getUserName();
					  Mail m=new Mail();
					  m.setSenderUserName(senderUserName);
					  m.setReceiverUserName(receiverUserName);
					  //m.setCcList(ccList);
					  m.setSubject(subject);
					  m.setType("unread");
					  m.setMailId(new ObjectId().toHexString());
					  m.setMessage(message);
					  m.setDate();
					  m.setTime();
					  
						  if(!fileIdListInString.equals("")||fileIdListInString!=null)
						  {
                       					  
							   for(String fileId:fileIdListInString)
							        {
									  if (ObjectId.isValid(fileId)){
									  fileIdList.add(new ObjectId(fileId));
								       }
							 
								      m.setFileIdList(fileIdList);
						 
					                 }
						   
						  }			  
					 try{
							 if(m.send(db)){
								 out.println("Successfully sent");
							 }
							 else{
								  out.println("send failed user not found");
							 }
						} 
				    catch(Exception es){
							es.printStackTrace();
					   }		 
		         }
				 else{
						request.getRequestDispatcher("/index.jsp").forward(request, response);
					}
            }
	}
}	   