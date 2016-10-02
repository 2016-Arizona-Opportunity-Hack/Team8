package beans;

import java.util.List;

/**
 * Created by nick on 10/1/16.
 */
public class Team {
//    {"team_name": "", "members": ["teamMember_0", "teamMember_1", "teamMember_2", "teamMember_3", "teamMember_4", "teamMember_5", "teamMember_6", "teamMember_7", "teamMember_8", "teamMember_9", "teamMember_10", "teamMember_11"], "team_miles": 0.0}

    // Sample JSON
    public String teamName;

    public List<Profile> members;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Profile> getMembers() {
        return members;
    }

    public void setMembers(List<Profile> members) {
        this.members = members;
    }

}
