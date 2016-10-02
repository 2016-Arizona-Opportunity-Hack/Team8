package mongohandler;

import beans.AccountID;
import beans.Profile;
import beans.Team;
import beans.TeamID;
import com.mongodb.BasicDBList;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nick on 10/1/16.
 */
public class MongoHandler implements MongoOperator {

    public MongoClient client;
    public MongoDatabase database;

    /**
     * Defining the constructors for the client
     */
    public MongoHandler() {
        client = new MongoClient(MongoConstants.DEFAULT_HOSTNAME, MongoConstants.DEFAULT_PORT);
        client.getDatabase(MongoConstants.DATABASE_NAME);
    }

    public MongoHandler(String hostname) {
        client = new MongoClient(hostname);
        client.getDatabase(MongoConstants.DATABASE_NAME);
    }

    public MongoHandler(String hostname, int port) {
        client = new MongoClient(hostname, port);
        client.getDatabase(MongoConstants.DATABASE_NAME);
    }

    @Override
    public boolean exists(AccountID account) throws MongoException{

        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);
        FindIterable<Document> dbAccount = collection.find(eq(MongoConstants.ACCOUNT_ID_FIELD, account.getAccountID()));

        // If the search returned null or the iterator has no elements in it
        if (dbAccount == null || !dbAccount.iterator().hasNext()){
            return false;
        }
        return true;
    }

    @Override
    public void populateProfile(Profile profile) throws MongoException{
        MongoDatabase database = client.getDatabase(MongoConstants.DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(MongoConstants.PROFILE_COLLECTION_NAME);

        Document document = new Document(MongoConstants.ACCOUNT_ID_FIELD, profile.getId())
                .append(MongoConstants.FIRST_NAME_FIELD, profile.getFirstName())
                .append(MongoConstants.LAST_NAME_FIELD, profile.getLastName())
                .append(MongoConstants.TOTAL_MILES_FIELD, profile.getTotalMiles());
        collection.insertOne(document);
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
            return matchedTeam;
        }
        return null;

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
    public void addTeamMember(Team team, Profile newTeamMember) throws MongoException{

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