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
import javax.servlet.http.Part;


@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class UploadFile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
		 DB db=(DB)getServletContext().getAttribute("Database");
		 HttpSession session = request.getSession(false);
		 PrintWriter out=response.getWriter();		
		 Part item = request.getPart("myFile");
		 try{
				String contentType =item.getContentType();
				String fileName=item.getName();
				long fileSize=item.getSize();
				InputStream inputStream = item.getInputStream();
			    ObjectId fileId=fileId=db.uploadFile(fileName,contentType,fileSize,inputStream );
				response.setHeader("fileId",fileId.toHexString());
		  }catch(Exception ex){}			  
			
    
	   }
}	