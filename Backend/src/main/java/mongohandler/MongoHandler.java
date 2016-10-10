package mongohandler;

import beans.*;
import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;
import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
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
                new Document("authorizations.service", "facebook").append("authorizations.Id", facebook.getId())
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
                new Document("authorizations.service", "google").append("authorizations.Id", google.getId())
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
                new Document("authorizations.service", "google").append("authorizations.Id", twitter.getId())
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
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> profileCollection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        MongoIterable<Document> foundProfiles = profileCollection.find(eq(MongoConstants.ACCOUNT_ID_FIELD, accountID.getAccountID()));
        Iterator<Document> foundProfilesIterator = foundProfiles.iterator();
        if (foundProfilesIterator.hasNext()){
            Document foundDocument = foundProfilesIterator.next();
            Profile requestedProfile = new Profile();
            requestedProfile.setFirstName(foundDocument.getString(MongoConstants.FIRST_NAME_FIELD));
            requestedProfile.setLastName(foundDocument.getString(MongoConstants.LAST_NAME_FIELD));
            requestedProfile.setTotalMiles(foundDocument.getInteger(MongoConstants.TOTAL_MILES_FIELD));
            requestedProfile.setId(foundDocument.getString(MongoConstants.ACCOUNT_ID_FIELD));
            return requestedProfile;
        }
        throw new MongoException("Could not find account with account id" + accountID.getAccountID());


    }

    @Override
    public void populateTeam(Team team) throws MongoException{
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(MongoConstants.TEAM_COLLECTION_NAME);

        BasicDBList teamMembers = new BasicDBList();
        for (Profile profile : team.getMembers()){
            teamMembers.add(profile.getId());
        }

        Document document = new Document(MongoConstants.TEAM_ID_FIELD, team.getTeamName())
                .append(MongoConstants.TEAM_MEMBERS_FIELD, teamMembers);

        collection.insertOne(document);

    }

    @Override
    public Team getTeam(TeamID teamID)  throws MongoException{
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(MongoConstants.TEAM_COLLECTION_NAME);
        FindIterable<Document> found = collection.find(eq(MongoConstants.TEAM_ID_FIELD, teamID.getTeamID()));

        Team matchedTeam = new Team();
        // TODO: add exception if multiple documents are found
        if (found.iterator().hasNext()){
            Document matchedDoc = found.iterator().next();
            matchedTeam.setTeamName(matchedDoc.getString(MongoConstants.TEAM_ID_FIELD));
            BasicDBList teamMembers =  (BasicDBList)matchedDoc.get(MongoConstants.TEAM_MEMBERS_FIELD);
            List<Profile> teamMemberList = new LinkedList<>();
            for (Object teamMember : teamMembers){
                Profile teamMemberProfile = getProfileBean(teamMember);
                teamMemberList.add(teamMemberProfile);
            }
            matchedTeam.setMembers(teamMemberList);
            matchedTeam.setTeamMiles(getTeamMiles(teamID));

            return matchedTeam;
        }
        return null;

    }

    @Override
    public void addTeamMember(Team team, Profile newTeamMember) throws MongoException{

    }

    @Override
    public void logMiles(AccountID accountID, LoggedMiles miles) throws MongoException {
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> profileCollection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        Iterator<Document> foundProfilesIterator = profileCollection.find(eq(MongoConstants.ACCOUNT_ID_FIELD, accountID.getAccountID())).iterator();


        if (foundProfilesIterator.hasNext()){
            Document doc = foundProfilesIterator.next();
            int totalMiles = doc.getInteger(MongoConstants.TOTAL_MILES_FIELD);
            totalMiles += miles.getMiles();

            // Update users total count
            profileCollection.updateOne(new Document(MongoConstants.ACCOUNT_ID_FIELD, accountID.getAccountID()),
                    new Document("$set", new Document(MongoConstants.TOTAL_MILES_FIELD, totalMiles)));
            // Add a new miles log to collection
            addMilesLogToMilesCollection(miles);

        }
    }

    void addMilesLogToMilesCollection(LoggedMiles miles){
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> loggedMilesCollection = database.getCollection(MongoConstants.LOGGED_MILES_COLLECTION_NAME);
        Document doc = new Document(MongoConstants.ACCOUNT_ID_FIELD, miles.accountId)
                .append(MongoConstants.MILES_FIELD, miles.getMiles())
                .append(MongoConstants.DATE_FIELD, miles.getDate());
        loggedMilesCollection.insertOne(doc);
    }

    @Override
    public int getTeamMiles(TeamID teamID) throws MongoException {
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> teamCollection = database.getCollection(MongoConstants.TEAM_COLLECTION_NAME);
        Iterator<Document> teamIterator = teamCollection.find(eq(MongoConstants.TEAM_ID_FIELD, teamID.getTeamID())).iterator();
        if (teamIterator.hasNext()){
            Document doc = teamIterator.next();
            List<String> teamMembers = (List<String>)doc.get(MongoConstants.TEAM_MEMBERS_FIELD);
            int teamMiles = 0;
            for (String account : teamMembers){
                AccountID accountID = new AccountID((String)account);
                teamMiles += getProfileTotalMiles(accountID);
            }
            return teamMiles;

        }
        throw new MongoException("A problem occured while getting team data");


    }
    // =================================================================================================================
    // HELPER METHODS
    // =================================================================================================================

    Profile convertToProfile(Document doc){

    }

    Profile createProfileFromAuth(FacebookAuth auth) throws MongoException{
        Map<String, Object> profileObj = new HashMap<>();
        profileObj.put("totalMiles", 0);
        profileObj.put("teamName", "");
        profileObj.put("Authorizations",)
        new Document(profileObj);

    }
    
    Profile createProfileFromAuth(GoogleAuth auth) throws MongoException {
        
    }
    
    Profile createProfileFromAuth(TwitterAuth auth) throws MongoException {
        
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