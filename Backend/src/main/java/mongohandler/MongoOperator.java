package mongohandler;

import beans.Profile;
import beans.Team;
import beans.AccountID;
import beans.TeamID;

import java.util.List;

/**
 * Created by nick on 10/1/16.
 */
public interface MongoOperator {

    boolean exists (AccountID account);

    void populateProfile(Profile profile);

    void populateTeam(Team team);

    Team getTeam(TeamID teamID);

    Profile getProfile(AccountID accountID);

    void addTeamMember(Team team, Profile newTeamMember);
}
