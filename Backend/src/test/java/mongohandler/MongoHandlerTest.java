package mongohandler;


import beans.AccountID;
import beans.Profile;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.testng.Assert;
import static com.mongodb.client.model.Filters.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Iterator;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandlerTest{

    MongoClient client;
    MongoDatabase mongoDatabase;

    @BeforeClass
    public void setup() throws Exception {
        client = new MongoClient();
        mongoDatabase = client.getDatabase(MongoConstants.DATABASE_NAME);
    }

    public Document getTestDocument(){
        Document testProfile = new Document(MongoConstants.FIRST_NAME_FIELD, "TEST")
                .append(MongoConstants.LAST_NAME_FIELD, "PROFILE")
                .append(MongoConstants.TEAM_ID_FIELD, "")
                .append(MongoConstants.TOTAL_MILES_FIELD, 100)
                .append(MongoConstants.ACCOUNT_ID_FIELD, "test@test.com");
        return testProfile;
    }

    @Test
    public void testDBConnection() throws Exception {
        MongoClient client = new MongoClient();
        Iterable<String> databases = client.listDatabaseNames();
        Iterator dbIterator = databases.iterator();
        while(dbIterator.hasNext()){
            System.out.println(dbIterator.next());
        }
    }

    @Test
    public void profileExistsTest() throws Exception {
        MongoCollection<Document> profileCollection = mongoDatabase.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);

        Document testProfile = getTestDocument();
        profileCollection.insertOne(testProfile);

        MongoHandler handler = new MongoHandler();

        AccountID testID = new AccountID();
        testID.setAccountID("test@test.com");
        Assert.assertTrue(handler.exists(testID), "The test ID does exist in the DB");

        testID.setAccountID("negative@test.com");
        Assert.assertFalse(handler.exists(testID), "The test ID Does not exist in DB");

    }

    @Test
    public void populateProfileTest() throws Exception {
        Profile testProfile = new Profile();
        testProfile.setTeamName("");
        testProfile.setId("test1@test.com");
        testProfile.setLastName("TEST_LAST");
        testProfile.setFirstName("TEST_FIRST");
        testProfile.setTotalMiles(0);

        MongoHandler handler = new MongoHandler();
        handler.populateProfile(testProfile);

        MongoCollection<Document> profileCollection = mongoDatabase.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        MongoIterable<Document> searchRestult = profileCollection.find(eq(MongoConstants.ACCOUNT_ID_FIELD, "test1@test.com"));
    }


}
