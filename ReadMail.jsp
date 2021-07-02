<%@page language="java" contentType="text/html"%>
<%@ page import="com.example.model.*,javax.servlet.http.*" %>
<%@ page import="com.example.model.Mail" %>
<%@ page import="com.example.model.DB" %>






<jsp:useBean id="user" class="com.example.model.User" scope="session" />
<%pageContext.setAttribute("inbox",((DB) getServletContext().getAttribute("Database")).getInbox(user.getUserName()),PageContext.PAGE_SCOPE);%>
<jsp:useBean id="inbox" class="com.example.model.Inbox" scope="page" />	
<% Mail m=inbox.getUnreadMail().get(Integer.parseInt(request.getParameter("mailId")));%>
<% request.getSession().setAttribute("Mail",m);%>
	

                
				
	<div class="inbox-head">
		<h3>View Mail</h3>
		<form class="pull-right position" action="#">
			<div class="input-append">
				<input type="text" placeholder="Search Mail" class="sr-input">
				<button type="button" class="btn sr-btn" data-original-title="" title=""><i class="fa fa-search"></i></button>
			</div>
		</form>
	</div>
	<div class="inbox-body">
		<div class="heading-inbox row">
			<div class="col-xs-8">
				<div class="compose-btn">
					<a class="btn btn-sm btn-primary" href="mail_compose.html" data-original-title="" title=""><i class="fa fa-reply"></i> Reply</a>
					<button title="" data-placement="top" data-toggle="tooltip" type="button" data-original-title="Print" class="btn  btn-sm tooltips"><i class="fa fa-print"></i> </button>
					<button title="" data-placement="top" data-toggle="tooltip" data-original-title="Trash" class="btn btn-sm tooltips"><i class="fa fa-trash-o"></i></button>
				</div>
			</div>
			<div class="col-xs-4 text-right">
				<p class="date"><%=m.getDate()%></p>
			</div>
			<div class="col-xs-12">
				<h4> <%=m.getSubject()%></h4>
			</div>
		</div>
		<div class="sender-info">
			<div class="row">
				<div class="col-xs-12">
					<img alt="" src="https://bootdey.com/img/Content/avatar/avatar6.png">
					<strong><%=m.getSenderUserName()%></strong>
					<span></span>
					to
					<strong>me</strong>
					<a class="sender-dropdown " href="javascript:;">
						<i class="fa fa-chevron-down"></i>
					</a>
				</div>
			</div>
		</div>
		<div class="view-mail">
		   <%=m.getMessage()%>
		
    		</div>
			<%if(m.getFileIdList()!=null){%>
				<div class="attachment-mail">
					<jsp:include page="attachement.jsp" />
				</div>
			<%}%>	
		<div class="compose-btn pull-left">
			<a class="btn btn-sm btn-primary" href="mail_compose.html" data-original-title="" title=""><i class="fa fa-reply"></i> Reply</a>
			<button class="btn btn-sm " data-original-title="" title=""><i class="fa fa-arrow-right"></i> Forward</button>
			<button title="" data-placement="top" data-toggle="tooltip" type="button" data-original-title="Print" class="btn  btn-sm tooltips"><i class="fa fa-print"></i> </button>
			<button title="" data-placement="top" data-toggle="tooltip" data-original-title="Trash" class="btn btn-sm tooltips"><i class="fa fa-trash-o"></i></button>
		</div>
	</div>