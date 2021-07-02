package com.example.model;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.util.*;
import org.bson.types.ObjectId;

public class Inbox
  { 
    String userName;
	ObjectId id;
	private List<Mail> readMail;
	private List<Mail>favourites;
	private List<Mail>unreadMail;
	private List<Mail>spam;
	private List<Mail>trash;
	
	public List<Mail> getReadMail()
	{
		return readMail;
    }
	public String getUserName()
	{
		return userName;
    }
	public List<Mail> getSpam()
	{
		return spam;
    }
	public List<Mail> getUnreadMail()
	{
		return unreadMail;
    }
	public List<Mail> getFavourites()
	{
		return favourites;
    }
	public List<Mail> getTrash()
	{
		return trash;
    }
	
	
	
	
	
	
	
	
	public void setReadMail(List<Mail> readMail)
	{
		this.readMail=readMail;
    }
	public void setUserName(String userName)
	{
		this.userName= userName;
    }
	public void setSpam(List<Mail> spam)
	{
		this.spam=spam;
    }
	public void setUnreadMail(List<Mail> unreadMail)
	{
		 this.unreadMail=unreadMail;
    }
	public void setFavourites(List<Mail> favourites)
	{
		this.favourites=favourites;
    }
	public void setTrash(List<Mail> trash)
	{
		this.trash=trash;
    }
	public ObjectId getId() {
     return id;
    }
	public void setId(ObjectId id) {
    this.id = id;
  }
	public Inbox withNewId() {
    setId(new ObjectId());
    return this;
	}
  }