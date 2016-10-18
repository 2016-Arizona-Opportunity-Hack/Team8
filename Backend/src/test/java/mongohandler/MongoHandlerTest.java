package mongohandler;


import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;
import beans.Profile;
import com.mongodb.MongoClient;
import mongohandler.constants.MongoConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandlerTest{

    MongoHandler testHandler;


    @BeforeClass
    public void dataSetup() {
        MongoClient client = MongoConnection.getMongoClient("localhost", MongoConstants.DEFAULT_PORT);
        client.dropDatabase("TESTDB");
    }

    @BeforeTest
    public void setup() throws Exception {
        testHandler = new MongoHandler("localhost", MongoConstants.DEFAULT_PORT, "TESTDB");
    }


    @Test
    public void getProfileFacebookAuthTest() throws Exception{
        FacebookAuth auth = new FacebookAuth();
        auth.setName("TEST NAME");
        auth.setToken("TEST TOKEN");
        auth.setEmail("TEST@TEST.com");
        auth.setId("ABCDEFG");

        Profile newProfile = testHandler.getProfile(auth);
        Profile expectedProfile = new Profile();
        expectedProfile.setFacebook(auth);
        expectedProfile.setName(auth.name);
        expectedProfile.setTotalMiles(0);
        expectedProfile.setId(newProfile.id);
        expectedProfile.setTwitter(new TwitterAuth());
        expectedProfile.setGoogle(new GoogleAuth());

        Assert.assertEquals(newProfile, expectedProfile, "Test that new profile is returned");

        Assert.assertEquals(testHandler.profileCollection.count(), 1, "There is only the new profile in the database");

        Profile anotherProfile = testHandler.getProfile(auth);
        Assert.assertEquals(testHandler.profileCollection.count(), 1, "Subsequent calls to get profile do not result in additional profiles");


    }

}
