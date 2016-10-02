package gallentserver;
import beans.AccountID;
import beans.Profile;
import beans.Team;
import beans.TeamID;
import mongohandler.MongoHandler;
import mongohandler.MongoOperator;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by vishal on 01/10/16.
 */

@Path("profile")
public class ProfileWS {


    MongoOperator mongoOperator = new MongoHandler();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProfile(@HeaderParam("accountID") String accountId) {

        System.out.println("account ID " + accountId);

        AccountID accountID = new AccountID();
        accountID.setAccountID(accountId);

        Profile profile = mongoOperator.getProfile(accountID);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(profile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonInString;


    }


}
