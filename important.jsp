<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.DB" %>
<jsp:useBean id="user" class="com.example.model.User" scope="session" />
<%pageContext.setAttribute("inbox",((DB) getServletContext().getAttribute("Database")).getInbox(user.getUserName()),PageContext.PAGE_SCOPE);%>
<jsp:useBean id="inbox" class="com.example.model.Inbox" scope="page" />





<c:forEach var="mail" items="${inbox.favourites}"  varStatus="mailLoopCount">
								 
								 <tr class="unread container">				 
									<td class="inbox-small-cells col-xs-1">
										<input type="checkbox" name="mailId" value=${mail.mailId} class="mail-checkbox">
									</td>
								    <td class="inbox-small-cells col-xs-1" id=${mail.mailId}><i class="fa fa-star"></i></td>
									<td class="view-message  dont-show col-xs-3" onclick="readMail(${mailLoopCount.count-1})">${mail.senderUserName}</td>
									<td class="view-message col-xs-3" onclick="readMail(${mailLoopCount.count-1})">${mail.subject}</td>
									<td class="view-message  inbox-small-cells col-xs-1"><i class="fa fa-paperclip"></i></td>
									<td class="view-message  text-right col-xs-4" onclick="readMail(${mailLoopCount.count-1})">${mail.time}</td>
								 </div>	
								</tr>
								
</c:forEach>