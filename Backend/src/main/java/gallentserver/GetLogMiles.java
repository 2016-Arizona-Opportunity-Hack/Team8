package gallentserver;

import beans.Team;
import beans.TeamID;
import mongohandler.MongoException;
import mongohandler.MongoHandler;
import mongohandler.MongoOperator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by vishal on 02/10/16.
 */
public class GetLogMiles {

    MongoOperator mongoOperator = new MongoHandler();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getMiles(@HeaderParam("teamID") String teamId){
        Object result = null;
        JSONObject jobject = null;
        String status = null;
        StringBuilder sb = new StringBuilder();

        System.out.println("team ID " +teamId);

        TeamID teamID = new TeamID();
        teamID.setTeamID(teamId);
        int  miles = 0;
        try {
            miles = mongoOperator.getTeamMiles(teamID);
        } catch (MongoException e) {
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


        //  callMongoForTeam(crunchifyBuilder.toString());





        return Integer.toString(miles);


    }
}
