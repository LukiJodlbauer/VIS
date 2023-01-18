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
public class HelloRestServer extends ResourceConfig {

    public HelloRestServer(){
        register(this.getClass());
        register(SimpleExceptionController.class);
        register(ParameterBranch.class);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getOverivew(@Context UriInfo uriInfo) {
        return "<html> <head> <title>Landing Page</title> </head>"
                + "<body> <p>Du befindest dich auf: "+uriInfo.getAbsolutePath().toString()+"</p> </br"+
                "<p>QueryParameters: "+ Arrays.toString(uriInfo.getQueryParameters().keySet().toArray()) +"</p></body></html>";
    }
    @GET
    @Path("/header")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getSimpleMessageJson() {
        return "{\"simpleMessage\": \"This is a simple message\"}";
    }

    @GET
    @Path("/header")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_XML)
    public String getSimpleMessageXML() {
        return "<simpleMessage>This is a simple message</simpleMessage>";
    }
    @GET
    @Path("/header")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getSimpleMessageText() {
        return "This is a simple message";
    }

}