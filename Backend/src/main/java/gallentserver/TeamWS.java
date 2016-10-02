package gallentserver;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vishal on 01/10/16.
 */
@Path("team")
public class TeamWS {


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTeam(final InputStream incomingData) {

        Object result = null;
        JSONObject jobject = null;
        String status = null;
        StringBuilder sb = new StringBuilder();

        StringBuilder crunchifyBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }

        System.out.println("Data Received: " + crunchifyBuilder.toString());
        ObjectMapper mapper = new ObjectMapper();



        try {
            jobject = new JSONObject(crunchifyBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "test";
    }

}
