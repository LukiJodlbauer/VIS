package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Arrays;

/**
 * Basic RMI Client-Class for testing Client and Server Communication.
 */
@ApplicationPath("*")
@Path("/")
public class EnvironmentRestServer extends ResourceConfig {

    public EnvironmentRestServer(){
        register(this.getClass());
        register(SimpleExceptionController.class);
        register(InfoBranch.class);
        register(DataBranch.class);
        register(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        // register(ParameterBranch.class);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getOverivew(@Context UriInfo uriInfo) {
        return "<html> <head> <title>Du befindest dich auf dem Environment Sensor Server</title> </head>"
                + "<body> <p>Die URL lautet: "+uriInfo.getAbsolutePath().toString()+"</p> </br"+
                "<p>QueryParameters: "+ Arrays.toString(uriInfo.getQueryParameters().keySet().toArray()) +"</p></body></html>";
    }
}