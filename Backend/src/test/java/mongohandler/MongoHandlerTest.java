package mongohandler;


import beans.AuthBeans.FacebookAuth;
import beans.Profile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandlerTest{

    MongoHandler testHandler;

    @BeforeTest
    public void setup() throws Exception {
        testHandler = new MongoHandler("localhost");
        // Run all test cases in the TESTDB database. All collections in the database should mirror the actual db
        testHandler.database = testHandler.client.getDatabase("TESTDB");
    }


    @Test
    public void getProfileFacebookAuthTest() throws Exception{
        MongoHandler handler = new MongoHandler();
        FacebookAuth auth = new FacebookAuth();
        auth.setName("TEST NAME");
        auth.setToken("TEST TOKEN");
        auth.setEmail("TEST@TEST.com");
        auth.setId("ABCDEFG");

        Profile newProfile = handler.getProfile(auth);
        Profile expectedProfile = new Profile();
        expectedProfile.setFacebook(auth);
        expectedProfile.setName(auth.name);
        expectedProfile.setTotalMiles(0);
        expectedProfile.setId(newProfile.id);


    }

}
