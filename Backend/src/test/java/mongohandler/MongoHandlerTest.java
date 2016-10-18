package mongohandler;


import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;
import beans.Profile;
import com.mongodb.MongoClient;
import mongohandler.constants.AuthenticationConstants;
import mongohandler.constants.MongoConstants;
import mongohandler.constants.ProfileConstants;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;
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

        final long startCollectionSize = testHandler.profileCollection.count();

        Profile newProfile = testHandler.getProfile(auth);
        Profile expectedProfile = new Profile();
        expectedProfile.setFacebook(auth);
        expectedProfile.setName(auth.name);
        expectedProfile.setTotalMiles(0);
        expectedProfile.setId(newProfile.id);
        expectedProfile.setTwitter(new TwitterAuth());
        expectedProfile.setGoogle(new GoogleAuth());

        Assert.assertEquals(newProfile, expectedProfile, "Test that new profile is returned");

        Assert.assertEquals(testHandler.profileCollection.count(), startCollectionSize + 1, "A new profile should have been added to the database");

        Profile anotherProfile = testHandler.getProfile(auth);
        Assert.assertEquals(testHandler.profileCollection.count(), startCollectionSize + 1, "Subsequent calls to get profile do not result in additional profiles");

    }

    @Test
    public void convertToProfileTest() throws Exception {

        // Stub facebook and document for facebook
        FacebookAuth facebookAuth = new FacebookAuth();
        facebookAuth.setId("facebook123");
        facebookAuth.setName("facebook name");
        facebookAuth.setEmail("facebook email");
        facebookAuth.setToken("facebook token");

        Document facebookDoc = new Document(AuthenticationConstants.FACEBOOK_ID, "facebook123")
                .append(AuthenticationConstants.FACEBOOK_NAME, "facebook name")
                .append(AuthenticationConstants.FACEBOOK_EMAIL, "facebook email")
                .append(AuthenticationConstants.FACEBOOK_TOKEN, "facebook token");

        // Stub google and document for google
        GoogleAuth google = new GoogleAuth();
        google.setId("google123");
        google.setName("google name");
        google.setEmail("google email");
        google.setToken("google token");

        Document googleDoc = new Document(AuthenticationConstants.GOOGLE_ID, "google123")
                .append(AuthenticationConstants.GOOGLE_NAME, "google name")
                .append(AuthenticationConstants.GOOGLE_EMAIL, "google email")
                .append(AuthenticationConstants.GOOGLE_TOKEN, "google token");

        // Stub twitter and document for twitter
        TwitterAuth twitterAuth = new TwitterAuth();
        twitterAuth.setDisplayName("twitter display");
        twitterAuth.setUsername("twitter username");
        twitterAuth.setId("twitter123");
        twitterAuth.setToken("twitter token");

        Document twitterDoc = new Document(AuthenticationConstants.TWITTER_DISPLAY_NAME, "twitter display")
                .append(AuthenticationConstants.TWITTER_USERNAME, "twitter username")
                .append(AuthenticationConstants.TWITTER_ID, "twitter123")
                .append(AuthenticationConstants.TWITTER_TOKEN, "twitter token");

        // Create authentication
        Profile expectedProfile = new Profile();
        expectedProfile.setGoogle(google);
        expectedProfile.setFacebook(facebookAuth);
        expectedProfile.setTwitter(twitterAuth);
        Document authInput = new Document(AuthenticationConstants.FACEBOOK_AUTH, facebookDoc)
                .append(AuthenticationConstants.GOOGLE_AUTH, googleDoc)
                .append(AuthenticationConstants.TWITTER_AUTH, twitterDoc);


        // Add root document information
        Date d = new Date();
        Document input = new Document(ProfileConstants.NAME, "TEST NAME")
                .append(ProfileConstants.TEAM_NAME, "TEAM NAME")
                .append(ProfileConstants.TOTAL_MILES, 100)
                .append(ProfileConstants.AUTHENTICATIONS, authInput)
                .append("_id", new ObjectId(d));



        expectedProfile.setName("TEST NAME");
        expectedProfile.setTeamName("TEAM NAME");
        expectedProfile.setTotalMiles(100);


        Assert.assertEquals(testHandler.convertToProfile(input), expectedProfile);

    }

}
