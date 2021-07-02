package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class GetImage extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)
 throws IOException, ServletException {
	  response.setContentType("image/gif");
      DB db = (DB) getServletContext().getAttribute("Database");
	  Mail m=(Mail) request.getSession().getAttribute("Mail");
      byte[] imageBytes = db.getFile(m.getFileId());
      response.getOutputStream().write(imageBytes);
      response.getOutputStream().flush();
  } 
}  