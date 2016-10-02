package gallentserver;
import beans.AccountID;
import beans.LoggedMiles;
import beans.Profile;
import mongohandler.MongoException;
import mongohandler.MongoHandler;
import mongohandler.MongoOperator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vishal on 01/10/16.
 */
@Path("logmiles")
public class LogMilesWS {



    MongoOperator mongoOperator = new MongoHandler();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateMiles(final InputStream incomingData){

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
        String callStatus = "{\n" +
                "  \"status\": \"success\"\n" +
                "}";

        try {
            ObjectMapper mapper = new ObjectMapper();
            LoggedMiles loggedMiles = mapper.readValue(crunchifyBuilder.toString(), LoggedMiles.class);
            AccountID accountID = new AccountID(loggedMiles.getAccountId());
            mongoOperator.logMiles(accountID, loggedMiles);

        }  catch (JsonParseException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {
            callStatus = "{\n" +
                    "  \"status\": \"jsonError\"\n" +
                    "}";;
            e.printStackTrace();
        } catch (IOException e) {
            callStatus = "{\n" +
                    "  \"status\": \"ioError\"\n" +
                    "}";
            e.printStackTrace();
        } catch (MongoException e) {
            callStatus = "{\n" +
                    "  \"status\": \"mongoError\"\n" +
                    "}";
            e.printStackTrace();
        }


        return callStatus;

    }


    }




