package ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by vishal on 01/10/16.
 */

@Path("/hello")
public class DBUIComm {


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Beta" + "</title>"
                + "<body><h1>" + "Hello Alpha" + "</body></h1>" + "</html> ";
    }
}
