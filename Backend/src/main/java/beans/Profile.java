package beans;

import java.io.Serializable;

/**
 * Created by nick on 10/1/16.
 */
public class Profile {
    //{"total_miles": 0.0, "team_name": "", "last_name": "lastName", "first_name": "firstName", "ID": "ID123"}

   // private static final long serialVersionUID = 1L;
    public int totalMiles;

    public String teamName;

    public String firstName;

    public String lastName;

    public String id;


    public int getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(int totalMiles) {
        this.totalMiles = totalMiles;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
