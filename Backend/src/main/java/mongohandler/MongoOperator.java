package mongohandler;

import beans.Profile;
import beans.Team;
import beans.AccountID;

import java.util.List;

/**
 * Created by nick on 10/1/16.
 */
public interface MongoOperator {

    boolean exists (AccountID account);

    void populateProfile(Profile profile);

    void populateTeam(Team team);

    void getProfilesWithIDs(List<AccountID> accountIDs);

    void getProfiles(List<Profile> profiles);

    void addTeamMember(Team team, Profile newTeamMember);
}
