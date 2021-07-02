package com.example.model;
import com.mongodb.MongoClient;
import java.util.*;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

/**
 * Mongo decoder for Orders.
 */
public class InboxCodec implements CollectibleCodec<Inbox> {

    private final CodecRegistry registry;
    private final Codec<Document> documentCodec;
    private final MailConverter mailConverter;

    /**
     * Default constructor.
     */
    public InboxCodec() {
        this.registry = MongoClient.getDefaultCodecRegistry();
        this.documentCodec = this.registry.get(Document.class);
        this.mailConverter = new MailConverter();
    }

    /**
     * Codec constructor.
     * @param codec The existing codec to use.
     */
    public InboxCodec(Codec<Document> codec) {
        this.registry = MongoClient.getDefaultCodecRegistry();
        this.documentCodec = codec;
        this.mailConverter = new MailConverter();
    }
	
    public InboxCodec(CodecRegistry registry) {
        this.registry = registry;
        this.documentCodec = this.registry.get(Document.class);
        this.mailConverter = new MailConverter();
    }
    @Override
    public void encode(
                    BsonWriter writer,
                    Inbox inbox,
                    EncoderContext encoderContext
                ) {
         Document inboxDoc = new Document();
		 inboxDoc.append("_id",new ObjectId());
		 String userName=inbox.getUserName();
		 inboxDoc.append("userName",userName);
		 
		 List <Document> readMail=new ArrayList<>();
		 for(Mail m:inbox.getReadMail()){
			  readMail.add(this.mailConverter.convert(m));
		 }
		 
		 List <Document> unreadMail=new ArrayList<>();
		 for(Mail m:inbox.getUnreadMail()){
			  unreadMail.add(this.mailConverter.convert(m));
		 }
		 List <Document> favourites=new ArrayList<>();
		 for(Mail m:inbox.getFavourites()){
			  favourites.add(this.mailConverter.convert(m));
		 }
		 List <Document> spam=new ArrayList<>();
		 for(Mail m:inbox.getSpam()){
			  spam.add(this.mailConverter.convert(m));
		 }
		  List <Document> trash=new ArrayList<>();
		 for(Mail m:inbox.getTrash()){
			  trash.add(this.mailConverter.convert(m));
		 }
		 
		 
		 
		 
		 
		 
         inboxDoc.append("read",readMail);
		 inboxDoc.append("favourites",favourites);
		 inboxDoc.append("unread",unreadMail);
		 inboxDoc.append("spam",spam);
		 inboxDoc.append("trash",trash);

       documentCodec.encode(writer, inboxDoc, encoderContext);	
    }

    @Override
    public Class<Inbox> getEncoderClass() {
        return Inbox.class;
    }

    @Override
    public Inbox decode(BsonReader reader, DecoderContext decoderContext) {
        Document inboxDoc = documentCodec.decode(reader, decoderContext);
        String userName =inboxDoc.getString("userName");
        Inbox inbox = new Inbox();
		ArrayList<Mail> readMail=new ArrayList<>();
	    ArrayList<Mail> unreadMail=new ArrayList<>();
	    ArrayList<Mail> favourites=new ArrayList<>();
	    ArrayList<Mail> spam=new ArrayList<>();
		ArrayList<Mail> trash=new ArrayList<>();
        ArrayList<Document> readMailDocArr = (ArrayList) inboxDoc.get("read");
        for (Document doc : readMailDocArr) {
             Mail m = this.mailConverter.convert(doc);
             readMail.add(m);
        }
		ArrayList<Document> unreadMailDocArr = (ArrayList) inboxDoc.get("unread");
        for (Document doc : unreadMailDocArr) {
             Mail m = this.mailConverter.convert(doc);
             unreadMail.add(m);
        }
		ArrayList<Document> spamMailDocArr = (ArrayList) inboxDoc.get("favourites");
        for (Document doc : spamMailDocArr) {
             Mail m = this.mailConverter.convert(doc);
             favourites.add(m);
        }
		ArrayList<Document> favouriteMailDocArr= (ArrayList) inboxDoc.get("spam");
        for (Document doc : favouriteMailDocArr) {
             Mail m = this.mailConverter.convert(doc);
             spam.add(m);
        }
		ArrayList<Document> trashMailDocArr= (ArrayList) inboxDoc.get("trash");
        for (Document doc : trashMailDocArr) {
             Mail m = this.mailConverter.convert(doc);
             trash.add(m);
        }
        inbox.setUserName(userName);
		Collections.reverse(readMail); 
        inbox.setReadMail(readMail);
        Collections.reverse(favourites); 
        inbox.setFavourites(favourites);
        Collections.reverse(unreadMail); 
        inbox.setUnreadMail(unreadMail);
        Collections.reverse(spam);
        inbox.setSpam(spam);
        Collections.reverse(trash); 		
        inbox.setTrash(trash);

        return inbox;
    }

    @Override
    public Inbox generateIdIfAbsentFromDocument(Inbox inbox) {
    return !documentHasId(inbox) ? inbox.withNewId() : inbox;
    }

  @Override
  public boolean documentHasId(Inbox inbox) {
    return null != inbox.getId();
  }

  @Override
  public BsonString getDocumentId(Inbox inbox) {
    if (!documentHasId(inbox)) {
      throw new IllegalStateException("This document does not have an " + "_id");
    }

    return new BsonString(inbox.getUserName());
 }
}