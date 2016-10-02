package mongohandler;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by nick on 10/1/16.
 *
 * Creates the required collections in the MongoDatabase incase they do not already exists
 */
class MongoInitializer {

    static MongoClient client;

    /**
     * Defining constructors. Available only within the mongo handler package
     */
    MongoInitializer (){
        client = new MongoClient();
    }

    MongoInitializer (String hostname, int port){
        client = new MongoClient(hostname, port);
    }

    void initializeDB(){
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> profileCollection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
    }
}
