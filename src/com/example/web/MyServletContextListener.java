package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;  
public class MyServletContextListener implements ServletContextListener {
 public void contextInitialized(ServletContextEvent event) {
    ServletContext sc = event.getServletContext();
	String user=sc.getInitParameter("User");
	String password=sc.getInitParameter("Password");
    try{  
	 DB db=new DB(user,password);	
     sc.setAttribute("Database",db);	 
 }catch(Exception e){ System.out.println(e);}  
 } 
 public void contextDestroyed(ServletContextEvent event) {
 //code to close the database connection 

}
}
