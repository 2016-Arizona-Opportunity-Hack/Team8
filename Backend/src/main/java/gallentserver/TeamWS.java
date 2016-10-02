package gallentserver;

import beans.Team;
import beans.TeamID;
import mongohandler.MongoHandler;
import mongohandler.MongoOperator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vishal on 01/10/16.
 */
@Path("team")
public class TeamWS {


    MongoOperator mongoOperator = new MongoHandler();


    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public String getTeam(@HeaderParam("teamID") String teamId) {

        Object result = null;
        JSONObject jobject = null;
        String status = null;
        StringBuilder sb = new StringBuilder();

        System.out.println("team ID " +teamId);

        TeamID teamID = new TeamID();
        teamID.setTeamID(teamId);
        Team team = mongoOperator.getTeam(teamID);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(team);
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder crunchifyBuilder = new StringBuilder();
       /* try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }*/


        System.out.println("Data Received: " + crunchifyBuilder.toString());

      //  callMongoForTeam(crunchifyBuilder.toString());



        try {
            jobject = new JSONObject(crunchifyBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonInString;
    }


    private void callMongoForTeam(final String TeamJson){

        ObjectMapper mapper = new ObjectMapper();
        try {
            Team team = mapper.readValue(TeamJson, Team.class);
            System.out.println("team " + team.getTeamName() );
            System.out.println("team member " + team.getMembers().get(0).firstName );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
