package mongohandler;

import com.mongodb.MongoClient;
import mongohandler.constants.MongoConstants;

/**
 * Created by nick on 10/9/16.
 *
 * MongoClient Singleton Implementation
 */
public class MongoConnection {

    private static MongoClient client;

    public static MongoClient getMongoClient(){
        if (client == null){
            new MongoConnection();
        }
        return client;
    }


    // Has only package level permissions because it should only be used in testing
    static MongoClient getMongoClient(String hostname, int port){
        if (client == null){
            new MongoConnection(hostname, port);
        }
        return client;
    }


    // Constructors
    private MongoConnection(){
        this(MongoConstants.DEFAULT_HOSTNAME, MongoConstants.DEFAULT_PORT);
    }

    private MongoConnection(String hostname, int port){
        client = new MongoClient(hostname, port);
    }
}
