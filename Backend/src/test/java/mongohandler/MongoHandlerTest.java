package mongohandler;


import beans.AccountID;
import beans.Profile;
import com.mongodb.BasicDBList;
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

        Document testProfile1 = new Document(MongoConstants.FIRST_NAME_FIELD, "TEST")
                .append(MongoConstants.LAST_NAME_FIELD, "PROFILE")
                .append(MongoConstants.TEAM_ID_FIELD, "TEST_TEAM")
                .append(MongoConstants.TOTAL_MILES_FIELD, 100)
                .append(MongoConstants.ACCOUNT_ID_FIELD, "test@test.com");

        Document testProfile2 = new Document(MongoConstants.FIRST_NAME_FIELD, "TEST")
                .append(MongoConstants.LAST_NAME_FIELD, "PROFILE2")
                .append(MongoConstants.TEAM_ID_FIELD, "TEST_TEAM")
                .append(MongoConstants.TOTAL_MILES_FIELD, 50)
                .append(MongoConstants.ACCOUNT_ID_FIELD, "test2@test.com");

        MongoCollection<Document> profileCollection = mongoDatabase.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        profileCollection.insertOne(testProfile1);
        profileCollection.insertOne(testProfile2);


        BasicDBList list = new BasicDBList();
        list.add("test@test.com");
        list.add("test2@test.com");


        Document doc = new Document(MongoConstants.TEAM_ID_FIELD, "TEST_TEAM")
                .append(MongoConstants.TEAM_MEMBERS_FIELD, list);

        MongoCollection<Document> teamCollection = mongoDatabase.getCollection(MongoConstants.TEAM_COLLECTION_NAME);
        teamCollection.insertOne(doc);


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
