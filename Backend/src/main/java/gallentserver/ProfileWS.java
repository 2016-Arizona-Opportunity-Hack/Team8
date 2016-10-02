package gallentserver;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by vishal on 01/10/16.
 */

@Path("profile")
public class ProfileWS {



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getProfile() {
        return "test";
    }


}
