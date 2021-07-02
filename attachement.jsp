<%@page language="java" contentType="text/html"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.*,javax.servlet.http.*" %>
<%@ page import="com.example.model.Mail" %>
<%@ page import="com.example.model.DB" %>
<%@ page import="org.bson.Document" %>
<%@ page import="org.bson.types.ObjectId"%>


<%  Mail mail=(Mail)request.getSession().getAttribute("Mail");%>

<%DB db=(DB)getServletContext().getAttribute("Database"); %>	
	        <p>
				<span><i class="fa fa-paperclip"></i><%=mail.getFileIdList().size()%> attachments</span>
			</p>
			
		<ul>
			<% int fileIdNo=0;
			  for(ObjectId fileId:mail.getFileIdList()){
			    Document d= db.getFileInfo(fileId);%>
				<li>
					   <% String s=(String)d.get("content_type");
						  String[] arrOfStr = s.split("/", 2); 
						  if(arrOfStr[0].equals("image")){		   
					   %>
							<a href="downloadFile.do?fileIdNo=<%=fileIdNo%>" class="atch-thumb">
								<img src="downloadFile.do?fileIdNo=<%=fileIdNo%>">
							</a>
							<div class="file-name">
						       <%=d.get("fileName")%>
					         </div>
					        <span><%=(((Long)d.get("fileSize"))/1024)%> KB</span>
					        <div class="links">
								<a href="downloadFile.do?fileIdNo=<%=fileIdNo%>">View</a> -
								<a href="downloadFile.do?fileIdNo=<%=fileIdNo%>">Download</a>
					         </div>
					  <%} else{%>	
					     
					     <a href="downloadFile.do?fileIdNo=<%=fileIdNo%>"  class="attachment_download_btn"><i class="fa"></i></a>
						
						 <div class="links">
								<a href="downloadFile.do?fileIdNo=<%=fileIdNo%>">View</a> 
					     </div>
						 <div class="file-name">
						       <%=d.get("fileName")%>
					     </div>
						 <br>
						 <span><%=(((Long)d.get("fileSize"))/1024)%>KB</span> 
					 <% }
					 fileIdNo++;
			       }

				%>
				</li>
		</ul>
		<script>
		    let downloadButton = document.querySelector(".attachment_download_btn");
			if(downloadButton) {    
			  downloadButton.addEventListener('click', function(event) {
				event.preventDefault();
				
				/* Start loading process. */
				downloadButton.classList.add('loading');
				
				/* Set delay before switching from loading to success. */
				window.setTimeout(function() {
				  downloadButton.classList.remove('loading');
				  downloadButton.classList.add('success');
				}, 3000);
				
				/* Reset animation. */
				window.setTimeout(function() {
				  downloadButton.classList.remove('success');
				}, 8000);
			  });
			};
		</script>	