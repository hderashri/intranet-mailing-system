package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;  
import org.bson.types.ObjectId;

public class RemoveFiles extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	     DB db = (DB) getServletContext().getAttribute("Database");
	     List<String> fileIdListInString = Arrays.asList(request.getHeader("fileIdList").split(","));
			 for(String fileId:fileIdListInString)
			 {
			   db.remove(new ObjectId(fileId));
			 }	 
    }
} 
