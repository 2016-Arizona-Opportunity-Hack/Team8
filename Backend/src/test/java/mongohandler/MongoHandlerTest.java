package mongohandler;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.testng.annotations.Test;

import java.util.Iterator;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandlerTest{

    @Test
    public void testDBConnection() throws Exception {
        MongoClient client = new MongoClient();
//        MongoDatabase db = client.getDatabase(MongoConstants.DATABASE_NAME);
//        db.createCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        Iterable<String> databases = client.listDatabaseNames();
        Iterator dbIterator = databases.iterator();
        while(dbIterator.hasNext()){
            System.out.println(dbIterator.next());
        }
    }

    @Test
    public void testProfileExists() throws Exception {

    }
}
