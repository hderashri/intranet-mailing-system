package com.example.model;

import org.bson.*;
import org.bson.codecs.*;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.*;

public class UserCodec implements CollectibleCodec<User> {

  private final Codec<Document> documentCodec;

  public UserCodec() {
    super();
    this.documentCodec = new DocumentCodec();
  }

  public void encode(BsonWriter bsonWriter, User user, EncoderContext encoderContext) {


     
    Document userDoc = new Document();
    String firstName = user.getFirstName();
	String lastName = user.getLastName();
	String gender = user.getGender();
    String dob = user.getDob();
	String userName = user.getUserName();
	String password = user.getPassword();
	String phone = user.getPhone();


    userDoc.put("_id", new ObjectId());

    if (null != firstName) {
      userDoc.put("firstName", firstName);
    }
	if (null != userName) {
      userDoc.put("userName",userName);
    }
	if (null != lastName) {
      userDoc.put("lastName", lastName);
    }
	if (null != gender) {
      userDoc.put("gender", gender);
    }
	if (null != phone) {
      userDoc.put("phone", phone);
    }
	if (null != dob) {
      userDoc.put("dob", dob);
    }
    if (null != password) {
      userDoc.put("password", password);
    }
    documentCodec.encode(bsonWriter, userDoc, encoderContext);
 }	
	
	
	

  @SuppressWarnings("unchecked")
  @Override
  public User decode(BsonReader bsonReader, DecoderContext decoderContext) {
    Document userDoc = documentCodec.decode(bsonReader, decoderContext);
    String firstName =userDoc.getString("firstName");
	String lastName = userDoc.getString("lastName");
	String gender = userDoc.getString("gender");
    String dob =userDoc.getString("dob");
	String userName =userDoc.getString("userName");
	String password = userDoc.getString("password");
	String phone =userDoc.getString("phone");
    User user=new User();
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setGender(gender);
	user.setDob(dob);
	user.setUserName(userName);
	user.setPassword(password);
	user.setPhone(phone);
    return user;
  }

  @Override
  public Class<User> getEncoderClass() {
    return User.class;
  }

  @Override
  public User generateIdIfAbsentFromDocument(User user) {
    return !documentHasId(user) ? user.withNewId() : user;
  }

  @Override
  public boolean documentHasId(User user) {
    return null != user.getId();
  }

  @Override
  public BsonString getDocumentId(User user) {
    if (!documentHasId(user)) {
      throw new IllegalStateException("This document does not have an " + "_id");
    }

    return new BsonString(user.getUserName());
  }
}

