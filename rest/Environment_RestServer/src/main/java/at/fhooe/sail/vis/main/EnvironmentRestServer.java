package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Arrays;

/**
 * Basic Rest Server
 */
@ApplicationPath("*")
@Path("/")
public class EnvironmentRestServer extends ResourceConfig {

    /**
     * Constructor registers all used classes for endpoints
     */
    public EnvironmentRestServer(){
        register(this.getClass());
        register(SimpleExceptionController.class);
        register(InfoBranch.class);
        register(DataBranch.class);
        register(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        // register(ParameterBranch.class);
    }

    /**
     * Method that return standard landing page
     * @param uriInfo uriInfo for landing page information content
     * @return html landing page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getOverivew(@Context UriInfo uriInfo) {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("<html> <head> <title>Du befindest dich auf dem Environment Sensor Server</title> </head>"
                + "<body><h1>Du befindest dich auf dem Environment REST Server</h1> </br>  <p>Die URL lautet: "+uriInfo.getAbsolutePath().toString()+"</p> </br"+
                "<p>QueryParameters: "+ Arrays.toString(uriInfo.getQueryParameters().keySet().toArray()) +"</p></body></html>");
        bob.type(MediaType.TEXT_HTML);

        return bob.build();
    }
}