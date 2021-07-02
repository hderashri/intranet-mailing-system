package com.example.model;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
public class User
{
	private ObjectId id;
	private String firstName;        
	private String lastName;
	private String dob;
	private String gender;
	private String userName;
	private String password;
	private String phone;
	
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getDob()
	{
		return dob;
	}
	
	public String getGender()
	{
		return gender;
	}
	public String getUserName()
	{
		return userName;
	}
	public String getPassword()
	{
		return password;
	}
	public String getPhone()
	{
		return phone;
	}
	public ObjectId getId() {
     return id;
    }

  
  
  public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public void setDob(String dob)
	{
		this.dob=dob;
	}
	
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	public void setUserName(String userName)
	{
		this.userName=userName;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	 public void setId(ObjectId id) {
     this.id = id;
    }
  
  
	public User withNewId() {
    setId(new ObjectId());
    return this;
	
	
}
	
	
}