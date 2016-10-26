package mongohandler;

import beans.*;
import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;

/**
 * Created by nick on 10/1/16.
 */
public interface MongoOperator {

    void populateTeam(Team team) throws MongoException;

    Team getTeam(TeamID teamID) throws MongoException;

    // Methods for retreving profile for different kinds of authentications. Needed because each auth has different fields
    Profile getProfile(FacebookAuth facebook) throws MongoException;

    Profile getProfile(GoogleAuth google) throws MongoException;

    Profile getProfile(TwitterAuth twitter) throws MongoException;

    // Get a profile based on a unique accountID. AccountID is independent of auths ids
    Profile getProfile(AccountID accountID) throws MongoException;

    void addTeamMember(Team team, Profile newTeamMember) throws MongoException;

    void logMiles(AccountID accountID, LoggedMiles miles) throws MongoException;

    int getTeamMiles(TeamID teamID) throws MongoException;
}
