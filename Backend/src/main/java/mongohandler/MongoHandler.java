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

    @Override
    public Profile getProfile(AccountID accountID) throws MongoException{
        Iterator<Document> profile = profileCollection.find(new Document("_id", accountID.getAccountID())).iterator();
        Document request = profile.next();
        if (profile.hasNext()){
            throw new MongoException("Multiple Matching Accounts Found!");
        } else {
            return convertToProfile(request);
        }

    }

    @Override
    public void populateTeam(Team team) throws MongoException{
    }

    @Override
    public Team getTeam(TeamID teamID)  throws MongoException{
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
    }
    // =================================================================================================================
    // HELPER METHODS
    // =================================================================================================================

    Document getNewProfile(){

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

    }

    Profile createProfileFromAuth(FacebookAuth auth) throws MongoException{
        Document facebookInitial = getNewProfile();
        Document facebookAuth = (Document)facebookInitial.get("authorizations.facebook");
        facebookAuth.replace("id", auth.getId());
        facebookAuth.replace("token", auth.getToken());
        facebookAuth.replace("email", auth.getEmail());
        facebookAuth.replace("name", auth.getName());
        facebookAuth.replace("name", auth.getName());

        return convertToProfile(facebookInitial);

    }

    //TODO: Implement
    Profile createProfileFromAuth(GoogleAuth auth) throws MongoException {
        return null;
        
    }

    // TODO: Implement
    Profile createProfileFromAuth(TwitterAuth auth) throws MongoException {
        return null;

    }

    int getProfileTotalMiles(AccountID accountID) throws MongoException{
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> profileCollection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        Iterator<Document> searchResult = profileCollection.find(eq(MongoConstants.ACCOUNT_ID_FIELD, accountID.getAccountID())).iterator();
        if (searchResult.hasNext()){
            Document foundDoc = searchResult.next();
            return foundDoc.getInteger(MongoConstants.TOTAL_MILES_FIELD);
        }
        throw new MongoException("A problem occured while getting miles for account " + accountID);

    }

    //TODO: Add exception if problem with DB
    Profile getProfileBean(Object memberID){
        String id = (String) memberID;
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> documents = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        FindIterable<Document> account = documents.find(eq(MongoConstants.ACCOUNT_ID_FIELD, id));
        Profile requestedProfile = new Profile();
        if(account.iterator().hasNext()){
            Document profileDoc = account.iterator().next();
            requestedProfile.setFirstName(profileDoc.getString(MongoConstants.FIRST_NAME_FIELD));
            requestedProfile.setLastName(profileDoc.getString(MongoConstants.LAST_NAME_FIELD));
            requestedProfile.setTeamName(profileDoc.getString(MongoConstants.TEAM_ID_FIELD));
            requestedProfile.setTotalMiles(profileDoc.getInteger(MongoConstants.TOTAL_MILES_FIELD));
            return requestedProfile;
        }
        return null;
    }
}