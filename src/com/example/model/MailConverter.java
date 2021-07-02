package com.example.model;

import com.example.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;

public class MailConverter {
    public Document convert(Mail m) {
                 Document mailDocument=new Document();
				 ObjectId mailId=new ObjectId(m.getMailId()); 
				 String type=m.getType();
				 String senderUserName = m.getSenderUserName();
				 List<String> ccList = m.getCCList();
				 String subject= m.getSubject();
				 String message= m.getMessage();
				 String date=m.getDate();
				 String time=m.getTime();
	             List<ObjectId> fileIdList=m.getFileIdList();
				 
				mailDocument.append("_id",mailId);
				if (null != type) {
				  mailDocument.append("type",type);
				}
				else{
					mailDocument.append("type","unread");
				}
				if (null != senderUserName) {
				  mailDocument.append("senderUserName",senderUserName);
				}
				if (null != ccList) {
				  mailDocument.append("cc",ccList);
				}
				if (null != subject) {
				  mailDocument.append("subject",subject);
				}
				if (null != message) {
				  mailDocument.append("message",message);
				}
				
				if(null!=date){
					 mailDocument.append("date",date);
				}
				if(null!=time){
					 mailDocument.append("time",time);
				}
				if(null!=fileIdList){
					mailDocument.append("fileIdList",fileIdList);
				}
			return mailDocument;	
    }
    public Mail convert(Document mailDocument) {
		 String mailId=((ObjectId)mailDocument.get("_id")).toHexString();
		 String type=mailDocument.getString("type");
		 String senderUserName=mailDocument.getString("senderUserName");
		 List<String> ccList=(List<String>)mailDocument.get("cc");
		 String subject=mailDocument.getString("subject");
		 String message=mailDocument.getString("message");
		 String date=mailDocument.getString("date");
		 String time=mailDocument.getString("time");
		 List<ObjectId> fileIdList=(List<ObjectId>)mailDocument.get("fileIdList");
		 Mail m=new Mail();
		 m.setMailId(mailId);
		 m.setType(type);
		 m.setSenderUserName(senderUserName);
		 m.setCcList(ccList);
		 m.setSubject(subject);
		 m.setMessage(message);
		 m.setDate(date);
		 m.setTime(time);
		 m.setFileIdList(fileIdList);
		 return m;

    }
}