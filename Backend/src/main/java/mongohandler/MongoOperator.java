package mongohandler;

import beans.Profile;
import beans.Team;
import beans.AccountID;
import beans.TeamID;
import com.mongodb.Mongo;

import java.util.List;

/**
 * Created by nick on 10/1/16.
 */
public interface MongoOperator {

    boolean exists (AccountID account) throws MongoException;

    void populateProfile(Profile profile) throws MongoException;

    void populateTeam(Team team) throws MongoException;

    Team getTeam(TeamID teamID) throws MongoException;

    Profile getProfile(AccountID accountID) throws MongoException;

    void addTeamMember(Team team, Profile newTeamMember) throws MongoException;
}
