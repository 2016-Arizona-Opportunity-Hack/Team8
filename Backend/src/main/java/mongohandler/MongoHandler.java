package mongohandler;

import beans.*;
import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import mongohandler.constants.AuthenticationConstants;
import mongohandler.constants.MongoConstants;
import mongohandler.constants.ProfileConstants;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.*;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandler implements MongoOperator {

    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> profileCollection;
    MongoCollection<Document> teamCollection;
    MongoCollection<Document> activityCollection;

    /**
     * Defining the constructors for the client
     */
    public MongoHandler() {
        this(MongoConstants.DEFAULT_HOSTNAME, MongoConstants.DEFAULT_PORT);

    }

    public MongoHandler(String hostname) {
        this(hostname, MongoConstants.DEFAULT_PORT);
    }

    public MongoHandler(String hostname, int port) {
        client = MongoConnection.getMongoClient(hostname, port);
        database = client.getDatabase(MongoConstants.DATABASE_NAME);
        profileCollection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        teamCollection = database.getCollection(MongoConstants.TEAM_COLLECTION_NAME);
        activityCollection = database.getCollection(MongoConstants.ACTIVITY_COLLECTION_NAME);
    }


    // =================================================================================================================
    // Interface Methods
    // =================================================================================================================

    
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Get Profiles for different authentications 
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    /**
     * Method to get a profile in Mongo based on a Facebook Authentication Bean. If no existing profile can be found
     * a new one will be created.
     *
     * @param facebook A facebook authentication Bean
     * @throws MongoException If an error occurs while accessing the database
     * @return Either a new profile initialized with the facebook auth or an existing profile that contained this 
     * authorization. Caller will not be notified if the account is new or not.
     */
    @Override
    public Profile getProfile(FacebookAuth facebook) throws MongoException {
        FindIterable<Document> existingProfiles = profileCollection.find(
                new Document("authorizations.facebook.id", facebook.getId())
        );
        Iterator<Document> facebookProfile = existingProfiles.iterator();
        // If at least one profile was found
        if (facebookProfile.hasNext()){
            Document foundDoc = facebookProfile.next();
            // Two profiles were found that could match there is an error with the data!
            if (facebookProfile.hasNext()){
                throw new MongoException("More than one matching profile was found for facebook id " + facebook.getId());
            }
            else {
                return convertToProfile(foundDoc);
            }
        }
        else {
            return createProfileFromAuth(facebook);
        }
    }

    /**
     * Method to get a profile in Mongo based on a google Authentication Bean. If no existing profile can be found
     * a new one will be created.
     *
     * @param google A google authentication Bean
     * @throws MongoException If an error occurs while accessing the database
     * @return Either a new profile initialized with the google auth or an existing profile that contained this 
     * authorization. Caller will not be notified if the account is new or not.
     */
    @Override
    public Profile getProfile(GoogleAuth google) throws MongoException {
        FindIterable<Document> existingProfiles = profileCollection.find(
                new Document("authorizations.google.id", google.getId())
        );
        Iterator<Document> facebookProfile = existingProfiles.iterator();
        // If at least one profile was found
        if (facebookProfile.hasNext()){
            Document foundDoc = facebookProfile.next();
            // Two profiles were found that could match there is an error with the data!
            if (facebookProfile.hasNext()){
                throw new MongoException("More than one matching profile was found for facebook id " + google.getId());
            }
            else {
                return convertToProfile(foundDoc);
            }
        }
        else {
            return createProfileFromAuth(google);
        }
    }

    /**
     * Method to get a profile in Mongo based on a twitter Authentication Bean. If no existing profile can be found
     * a new one will be created.
     *
     * @param twitter A twitter authentication Bean
     * @throws MongoException If an error occurs while accessing the database
     * @return Either a new profile initialized with the twitter auth or an existing profile that contained this 
     * authorization. Caller will not be notified if the account is new or not.
     */
    @Override
    public Profile getProfile(TwitterAuth twitter) throws MongoException {
        FindIterable<Document> existingProfiles = profileCollection.find(
                new Document("authorizations.twitter", twitter.getId())
        );
        Iterator<Document> facebookProfile = existingProfiles.iterator();
        // If at least one profile was found
        if (facebookProfile.hasNext()){
            Document foundDoc = facebookProfile.next();
            // Two profiles were found that could match there is an error with the data!
            if (facebookProfile.hasNext()){
                throw new MongoException("More than one matching profile was found for facebook id " + twitter.getId());
            }
            else {
                return convertToProfile(foundDoc);
            }
        }
        else {
            return createProfileFromAuth(twitter);
        }
    }

    /**
     * Method that finds a profile based on the Run Ranger Run unique id. This id is currently the unique id assigned
     * to the profile document by mongo.
     *
     * @param accountID the accountID for the profile you want returned
     * @return The requested profile
     * @throws MongoException Throws an exception multiple accounts are found or the account does not exisit.
     */
    @Override
    public Profile getProfile(AccountID accountID) throws MongoException{
        Iterator<Document> profile = profileCollection.find(new Document("_id", accountID.getAccountID())).iterator();
        if (profile.hasNext()) {
            Document request = profile.next();
            if (profile.hasNext()){
                throw new MongoException("Multiple Matching Accounts Found!");
            } else {
                return convertToProfile(request);
            }
        } else {
            throw new MongoException("No matching account was found!");
        }

    }

    @Override
    public void populateTeam(Team team) throws MongoException{
    }

    @Override
    public Team getTeam(TeamID teamID)  throws MongoException{
        return new Team();
    }

    @Override
    public void addTeamMember(Team team, Profile newTeamMember) throws MongoException{

    }

    @Override
    public void logMiles(AccountID accountID, LoggedMiles miles) throws MongoException {
    }

    void addMilesLogToMilesCollection(LoggedMiles miles){
    }

    @Override
    public int getTeamMiles(TeamID teamID) throws MongoException {
        return 0;
    }

    // =================================================================================================================
    // HELPER METHODS
    // =================================================================================================================

    /**
     * Creates a new Profile document and sub documents that match the implemented schema.
     * @return a new document that has not been added to the DB. All fields are set to initial values
     */
    Document createNewProfileDoc(){

        // Top level profile
        Map<String, Object> profile = new HashMap<>();
        profile.put(ProfileConstants.TOTAL_MILES, 0);
        profile.put(ProfileConstants.NAME, "");
        profile.put(ProfileConstants.TEAM_NAME, "");

        // Create facebook object
        Map<String, Object> facebookAuth = new HashMap<>();
        facebookAuth.put(AuthenticationConstants.FACEBOOK_ID, "");
        facebookAuth.put(AuthenticationConstants.FACEBOOK_TOKEN, "");
        facebookAuth.put(AuthenticationConstants.FACEBOOK_EMAIL, "");
        facebookAuth.put(AuthenticationConstants.FACEBOOK_NAME, "");
        Document facebookDoc = new Document(facebookAuth);

        // Create Google Object
        Map<String, Object>googleAuth = new HashMap<>();
        googleAuth.put(AuthenticationConstants.GOOGLE_ID, "");
        googleAuth.put(AuthenticationConstants.GOOGLE_TOKEN, "");
        googleAuth.put(AuthenticationConstants.GOOGLE_EMAIL, "");
        googleAuth.put(AuthenticationConstants.GOOGLE_NAME, "");
        Document googleDoc = new Document(googleAuth);

        // Create Twitter Object
        Map<String, Object>twitterAuth = new HashMap<>();
        twitterAuth.put(AuthenticationConstants.TWITTER_ID, "");
        twitterAuth.put(AuthenticationConstants.TWITTER_TOKEN, "");
        twitterAuth.put(AuthenticationConstants.TWITTER_DISPLAY_NAME, "");
        twitterAuth.put(AuthenticationConstants.TWITTER_USERNAME, "");
        Document twitterDoc = new Document(twitterAuth);

        // Create a new document to hold each of the authentication docs
        Document authentications = new Document(AuthenticationConstants.FACEBOOK_AUTH, facebookDoc)
                .append(AuthenticationConstants.GOOGLE_AUTH, googleDoc)
                .append(AuthenticationConstants.TWITTER_AUTH, twitterDoc);
        profile.put(ProfileConstants.AUTHENTICATIONS, authentications);

        return new Document(profile);


    }

    Profile convertToProfile(Document doc){
        Profile profile = new Profile();
        profile.setName(doc.getString(ProfileConstants.NAME));
        profile.setId(doc.getObjectId("_id").toString());
        profile.setTeamName(doc.getString(ProfileConstants.TEAM_NAME));
        profile.setTotalMiles(doc.getInteger(ProfileConstants.TOTAL_MILES));

        // Set facebook Authentication
        FacebookAuth fb = new FacebookAuth();
        Document facebookDoc = (Document)
                ((Document)doc.get(ProfileConstants.AUTHENTICATIONS))
                        .get(AuthenticationConstants.FACEBOOK_AUTH);

        fb.setName(facebookDoc.getString(AuthenticationConstants.FACEBOOK_NAME));
        fb.setId(facebookDoc.getString(AuthenticationConstants.FACEBOOK_ID));
        fb.setEmail(facebookDoc.getString(AuthenticationConstants.FACEBOOK_EMAIL));
        fb.setToken(facebookDoc.getString(AuthenticationConstants.FACEBOOK_TOKEN));

        // Set google Authentication
        GoogleAuth google = new GoogleAuth();
        Document googleDoc = (Document)
                ((Document) doc.get(ProfileConstants.AUTHENTICATIONS)).get(AuthenticationConstants.GOOGLE_AUTH);

        google.setName(googleDoc.getString(AuthenticationConstants.GOOGLE_NAME));
        google.setEmail(googleDoc.getString(AuthenticationConstants.GOOGLE_EMAIL));
        google.setToken(googleDoc.getString(AuthenticationConstants.GOOGLE_TOKEN));
        google.setId(googleDoc.getString(AuthenticationConstants.GOOGLE_ID));

        // Set twitter Authentication
        TwitterAuth twitter = new TwitterAuth();
        Document twitterDoc = (Document)
                ((Document)doc.get(ProfileConstants.AUTHENTICATIONS)).get(AuthenticationConstants.TWITTER_AUTH);

        twitter.setId(twitterDoc.getString(AuthenticationConstants.TWITTER_ID));
        twitter.setToken(twitterDoc.getString(AuthenticationConstants.TWITTER_TOKEN));
        twitter.setDisplayName(twitterDoc.getString(AuthenticationConstants.TWITTER_DISPLAY_NAME));
        twitter.setUsername(twitterDoc.getString(AuthenticationConstants.TWITTER_USERNAME));

        // Add authentications to profile
        profile.setGoogle(google);
        profile.setTwitter(twitter);
        profile.setFacebook(fb);

        return profile;

    }

    Profile createProfileFromAuth(FacebookAuth auth) throws MongoException{
        Document facebookProfile = createNewProfileDoc();
        // Get the facebook authentication doc
        Document facebookAuth = (Document)
                ((Document)facebookProfile.get(ProfileConstants.AUTHENTICATIONS))
                        .get(AuthenticationConstants.FACEBOOK_AUTH);

        // Set the fields in the facebook authentication doc
        facebookAuth.replace(AuthenticationConstants.FACEBOOK_ID, auth.getId());
        facebookAuth.replace(AuthenticationConstants.FACEBOOK_TOKEN, auth.getToken());
        facebookAuth.replace(AuthenticationConstants.FACEBOOK_EMAIL, auth.getEmail());
        facebookAuth.replace(AuthenticationConstants.FACEBOOK_NAME, auth.getName());

        // Update profile fields
        facebookProfile.replace(ProfileConstants.NAME, auth.getName());

        // Insert new profile into db
        profileCollection.insertOne(facebookProfile);

        return convertToProfile(facebookProfile);

    }

    //TODO: Implement
    Profile createProfileFromAuth(GoogleAuth auth) throws MongoException {
        return null;
        
    }

    // TODO: Implement
    Profile createProfileFromAuth(TwitterAuth auth) throws MongoException {
        return null;

    }

}