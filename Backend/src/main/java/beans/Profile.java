package beans;

import beans.AuthBeans.FacebookAuth;
import beans.AuthBeans.GoogleAuth;
import beans.AuthBeans.TwitterAuth;

/**
 * Created by nick on 10/1/16.
 *
 *
 *
 *
 {
 facebook         : {
 id           : "",
 token        : "",
 email        : "",
 name         : ""
 },
 twitter          : {
 id           : "",
 token        : "",
 displayName  : "",
 username     : ""
 },
 google           : {
 id           : "",
 token        : "",
 email        : "",
 name         : ""
 },
 rangerID : "12345"
 }
 */
public class Profile {
    //{"total_miles": 0.0, "team_name": "", "last_name": "lastName", "first_name": "firstName", "ID": "ID123"}

   // private static final long serialVersionUID = 1L;
    public int totalMiles;
    public String teamName;
    public String name;
    public String id;
    public FacebookAuth facebook;
    public TwitterAuth twitter;
    public GoogleAuth google;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacebookAuth getFacebook() {
        return facebook;
    }

    public void setFacebook(FacebookAuth facebook) {
        this.facebook = facebook;
    }

    public TwitterAuth getTwitter() {
        return twitter;
    }

    public void setTwitter(TwitterAuth twitter) {
        this.twitter = twitter;
    }

    public GoogleAuth getGoogle() {
        return google;
    }

    public void setGoogle(GoogleAuth google) {
        this.google = google;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
