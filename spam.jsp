<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.DB" %>
<jsp:useBean id="user" class="com.example.model.User" scope="session" />
<%pageContext.setAttribute("inbox",((DB) getServletContext().getAttribute("Database")).getInbox(user.getUserName()),PageContext.PAGE_SCOPE);%>
<jsp:useBean id="inbox" class="com.example.model.Inbox" scope="page" />


<div class="inbox-head">
			    <div class="col-xs-3 col-xs-offset-1">
                    <h3>Inbox</h3>
				</div>	
                    <form class="pull-right position col-xs-8" action="#">
    
                            <input type="text" placeholder="Search Mail" class="sr-input">
                            <button type="button" class="btn sr-btn" data-original-title="" title=""><i class="fa fa-search"></i></button>
				
                    </form>
                </div>
                <div class="inbox-body">
					<div class="mail-option row" >
						<div class="chk-all col-xs-1">
							<input type="checkbox" class="mail-checkbox mail-group-checkbox">
							<div class="btn-group">
								<a class="btn mini all" href="#" data-toggle="dropdown" data-original-title="" title="">
									All
									<i class="fa fa-angle-down "></i>
								</a>
								<ul class="dropdown-menu">
									<li><a href="#"> None</a></li>
									<li><a href="#"> Read</a></li>
									<li><a href="#"> Unread</a></li>
								</ul>
							</div>
						</div>
						<div class="btn-group col-xs-1">
							<a class="btn mini tooltips" href="#" data-toggle="dropdown" data-placement="top" data-original-title="Refresh">
								<i class="fas fa-sync-alt"></i>
							</a>
						</div>
				
						<div class="btn-group col-xs-2">
							<a class="btn mini blue" href="#" data-toggle="dropdown" data-original-title="" title="">
								Move to
								<i class="fa fa-angle-down "></i>
							</a>
							<ul class="dropdown-menu">
								<li><a href=""><i class="fa fa-pencil"></i> Mark as Read</a></li>
								<li><a href="#"><i class="fa fa-ban"></i> Spam</a></li>
								<li class="divider"></li>
								<li><a href="javascript:deleteMail()"><i class="fa fa-trash-o"  ></i> Delete</a></li>
							</ul>
						</div> 
						<ul class="unstyled inbox-pagination">
							<li><span>1-50 of 234</span></li>
							<li>
								<a href="#" class="np-btn"><i class="fa fa-angle-left  pagination-left"></i></a>
							</li>
							<li>
								<a href="#" class="np-btn"><i class="fa fa-angle-right pagination-right"></i></a>
							</li>
						</ul>
					</div>
					 <table class="table table-inbox table-hover">
						 <tbody class="mailTable">
						     <c:forEach var="mail" items="${inbox.spam}"  varStatus="mailLoopCount">
								 
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
						</tbody>
					</table>
                </div>

