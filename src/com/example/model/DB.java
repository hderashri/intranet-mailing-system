package com.example.model;
import com.mongodb.client.FindIterable; 
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.MongoDatabase; 
import com.mongodb.client.model.Filters; 
import com.mongodb.client.model.Updates; 
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientOptions;
import java.util.*; 
import java.text.*;
import org.bson.Document;  
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;  
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.session.ClientSession;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.pojo.PropertyModelBuilder;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.MongoClientURI;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.fields;


public class DB {

	String user;
	String password;
	MongoDatabase database;
	MongoCollection<User> usersCollection;
	MongoCollection<Inbox> inboxCollection;
	GridFSBucket gridFSBucket;

	// constructor

	public DB(String user, String password) {
		this.user = user;
		this.password = password;
		MongoCredential credential = MongoCredential.createCredential(user, "mail", password.toCharArray());

		InboxCodec inboxCodec = new InboxCodec();
		UserCodec userCodec = new UserCodec();
		MailCodec mailCodec = new MailCodec();
		CodecRegistry codecRegistry =
				fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromCodecs(userCodec, inboxCodec, mailCodec));


		//MongoClient mongoClient = new MongoClient(new ServerAddress(""), Arrays.asList(credential), MongoClientOptions.builder().codecRegistry(codecRegistry).build());


		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://admin:Nishantd%401410@cluster0.mxukk.gcp.mongodb.net/mail?retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("mail");


		//database = mongoClient.getDatabase("mail");
		com.mongodb.DB db = mongoClient.getDB("mail");
		if (!db.collectionExists("Users")) {
			database.createCollection("Users");
		}
		if (!db.collectionExists("Inbox")) {
			database.createCollection("Inbox");
		}
		usersCollection = database.getCollection("Users", User.class).withCodecRegistry(codecRegistry);
		inboxCollection = database.getCollection("Inbox", Inbox.class).withCodecRegistry(codecRegistry);
		gridFSBucket = GridFSBuckets.create(database, "uploadedFiles");


	}

	public static void main(String[] args) {
		DB db = new DB("admin", "Nishantd@1410");
		  /*User u=new User();
		  u.setUserName("nishant.mishra");
		  u.setFirstName("Nishant");
		  u.setLastName("Mishra");
		  u.setPassword("12345");
		  db.createUser(u);
		   InputStream in = db.getFile("5f58e76e42ada60c34b699bf");
		   File outputfile = new File("C:\\Users\\Prashant\\Desktop\\MyProject\\saved.jpeg");
		   try{
			  outputfile.createNewFile();
			  BufferedImage bImage = ImageIO.read(in);
			  ImageIO.write(bImage, "jpeg", outputfile);
		  }catch(Exception ex){
		  }
		 */
	}

	public int createUser(User u) {
		Bson queryFilter = eq("userName", u.getUserName());
		long count = usersCollection.count(queryFilter);
		if (count == 0) {
			usersCollection.insertOne(u);
			inboxCollection.insertOne(createFormatInbox(u.getUserName()));
			return 1;
		} else {
			return 2;
		}
	}

	private Inbox createFormatInbox(String userName) {
		List<Mail> read = new ArrayList<Mail>();
		List<Mail> unread = new ArrayList<Mail>();
		List<Mail> favourites = new ArrayList<Mail>();
		List<Mail> spam = new ArrayList<Mail>();
		List<Mail> trash = new ArrayList<Mail>();
		List<String> cc = new ArrayList<String>();
		cc.add("");
		Mail m = new Mail();
		m.setSenderUserName("System");
		m.setCcList(cc);
		m.setSubject("Sytem generated mail");
		m.setMessage("This message is System generated");
		m.setDate();
		m.setTime();
		m.setMailId(new ObjectId().toHexString());
		m.setType("read");
		read.add(m);
		m.setType("unread");
		unread.add(m);
		m.setType("favourites");
		favourites.add(m);
		m.setType("spam");
		spam.add(m);
		m.setType("trash");
		trash.add(m);
		Inbox inbox = new Inbox();
		inbox.setUserName(userName);
		inbox.setReadMail(read);
		inbox.setFavourites(favourites);
		inbox.setUnreadMail(unread);
		inbox.setSpam(spam);
		inbox.setTrash(trash);
		return inbox;

	}

	public ObjectId uploadFile(String fileName, String contentType, long fileSize, InputStream inputStream) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		GridFSUploadOptions uploadOptions = new GridFSUploadOptions()
				.chunkSizeBytes(1024)
				.metadata(new Document("fileName", fileName)
						.append("upload_date", sdf.format(timestamp))
						.append("content_type", contentType)
						.append("fileSize", fileSize));
		ObjectId fileId = gridFSBucket.uploadFromStream(fileName, inputStream, uploadOptions);
		return fileId;
	}

	public Document getFileInfo(Object fileId) {
		GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream((ObjectId) fileId);
		Document metadata = downloadStream.getGridFSFile().getMetadata();
		return metadata;

	}

	public InputStream getFile(Object fileId) {
		GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream((ObjectId) fileId);
		return downloadStream;
	}

	public User findUser(String userName, String password) {
		Bson queryFilter = and(eq("userName", userName), eq("password", password));
		User user = usersCollection.find(queryFilter).iterator().tryNext();
		return user;
	}

	public Inbox getInbox(String userName) {
		Bson queryFilter = eq("userName", userName);
		Inbox inbox = inboxCollection.find(queryFilter).iterator().tryNext();
		return inbox;
	}

	public void uploadMail(Mail m, List<String> receivers) {
		for (String receiver : receivers) {
			Bson queryFilter = eq("userName", receiver);
			long count = inboxCollection.count(queryFilter);
			if (count == 1) {
				inboxCollection.updateOne(queryFilter, Updates.addToSet(m.getType(), m));
			}
		}
	}

	public Mail findMail(String userName, ObjectId mailId) {
		int categoryNo = 0;
		String[] categories = {"read", "unread", "favourites", "spam", "trash"};
		while (categoryNo < 5) {
			Inbox inbox = inboxCollection
					.find(and(eq("userName", userName), eq(categories[categoryNo] + "._id", mailId)))
					.iterator().tryNext();
			if (inbox != null) {
				if (inbox.getUnreadMail() != null) {
					return inbox.getUnreadMail().get(0);
				}
				if (inbox.getSpam() != null) {
					return inbox.getSpam().get(0);
				}
				if (inbox.getReadMail() != null) {
					return inbox.getReadMail().get(0);
				}
				if (inbox.getFavourites() != null) {
					return inbox.getFavourites().get(0);
				}
				if (inbox.getTrash() != null) {
					return inbox.getTrash().get(0);
				}
			}
			categoryNo++;
		}
		return null;


	}

	public void moveToRead(String mailId, String userName) {
		ObjectId mailObjectId = new ObjectId(mailId);
		Mail m = findMail(userName, mailObjectId);
		if (m != null) {
			deleteMail(m, false, userName);
			m.setType("read");
			Bson queryFilter = eq("userName", userName);
			inboxCollection.updateOne(queryFilter, Updates.addToSet("read", m));
		}

	}

	public void moveToSpam(String mailId, String userName) {

		ObjectId mailObjectId = new ObjectId(mailId);
		Mail m = findMail(userName, mailObjectId);
		deleteMail(m, false, userName);
		m.setType("spam");
		Bson queryFilter = eq("userName", userName);
		inboxCollection.updateOne(queryFilter, Updates.addToSet("spam", m));

	}

	public void moveToFavourites(String mailId, String userName) {
		ObjectId mailObjectId = new ObjectId(mailId);
		Mail m = findMail(userName, mailObjectId);
		deleteMail(m, false, userName);
		m.setType("favourites");
		Bson queryFilter = eq("userName", userName);
		inboxCollection.updateOne(queryFilter, Updates.addToSet("favourites", m));

	}

	public void moveToTrash(String mailId, String userName) {
		ObjectId mailObjectId = new ObjectId(mailId);
		Mail m = findMail(userName, mailObjectId);
		deleteMail(m, false, userName);
		m.setType("trash");
		Bson queryFilter = eq("userName", userName);
		inboxCollection.updateOne(queryFilter, Updates.addToSet("trash", m));

	}


	//delete Mail

	//remove files
	public void remove(ObjectId fileId) {
		gridFSBucket.delete(fileId);
	}

	public void deleteMail(Mail m, boolean permanentlyDelete, String userName) {

		//find files assciated to that mail
		List<ObjectId> fileIdList = m.getFileIdList();

		//delete mail
		Bson queryFilter = eq("userName", userName);
		Bson fields = new Document().append(m.getType(), new Document().append("_id", new ObjectId(m.getMailId())));
		Bson update = Updates.pullByFilter(fields);
		inboxCollection.updateOne(queryFilter, update);


		//if to be deleted permanently remove files associated to it;
		if (permanentlyDelete == true) {
			for (ObjectId fileId : fileIdList) {
				remove(fileId);
			}
		}
	}

	public boolean isUserNameExists(String userName) {
		Bson queryFilter = and(eq("userName", userName));
		long count = usersCollection.count(queryFilter);
		if (count == 1)
			return true;
		else
			return false;
	}

	public int setPassword(String userName, String phoneNo, String newPassword) {

		Bson queryFilter = and(eq("userName", userName), eq("phone", phoneNo));
		Bson updateOperation = set("password", newPassword);
		long count = usersCollection.count(queryFilter);
		if (count == 1) {
			usersCollection.updateOne(queryFilter, updateOperation);
			return 1;
		} else
			return -1;
	}

}

				