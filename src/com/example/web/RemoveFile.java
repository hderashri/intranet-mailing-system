package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;  
import org.bson.types.ObjectId;

public class RemoveFile extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	     DB db = (DB) getServletContext().getAttribute("Database");
	     String fileId =request.getHeader("fileId");
		 db.remove(new ObjectId(fileId));
    }
} 
