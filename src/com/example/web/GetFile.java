package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.bson.Document;
import org.bson.types.ObjectId;



public class GetFile extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	
	  
      DB db = (DB) getServletContext().getAttribute("Database");
	  Mail mail=(Mail)request.getSession().getAttribute("Mail");
	  int fileIdNo=Integer.parseInt(request.getParameter("fileIdNo"));
	  List<ObjectId> fileIdList=mail.getFileIdList();
	  ObjectId fileId=fileIdList.get(fileIdNo);
	  Document d= db.getFileInfo(fileId);
	  response.setContentType((String)d.get("content_type"));
	  response.setHeader("content-disposition", "inline; filename="+(String)d.get("fileName"));
	   OutputStream os=null;
	   InputStream in=null ;
	  try {
			  os = response.getOutputStream();
			  in = db.getFile(fileId);
			  byte[] buf = new byte[8192];
			  int length;
			  while ((length = in.read(buf)) > 0) {
				 os.write(buf, 0, length);
	            }
		      os.flush();
	     }catch(Exception e){}
		 finally{
			 if(in!=null)
			    in.close();
			 if(os!=null)
			    os.close();
		 }
  } 
}  