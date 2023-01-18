package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Arrays;

/**
 * Basic Hello World REST Server.
 */
@ApplicationPath("*")
@Path("/")
public class HelloRestServer extends ResourceConfig {
    /**
     * Constructor registers all used classes for endpoints
     */
    public HelloRestServer(){
        register(this.getClass());
        register(SimpleExceptionController.class);
        register(ParameterBranch.class);
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
        bob.entity("<html> <head> <title>Du befindest dich auf dem Hello Wordl REST Server</title> </head>"
                + "<body><h1>Du befindest dich auf dem Hello World REST Server</h1> </br> <p>Die URL lautet: "+uriInfo.getAbsolutePath().toString()+"</p> </br"+
                "<p>QueryParameters: "+ Arrays.toString(uriInfo.getQueryParameters().keySet().toArray()) +"</p></body></html>");
        bob.type(MediaType.TEXT_HTML);

        return bob.build();
    }

    /**
     * Dummy method that return a simple message as JSON
     * @return simple message as JSON
     */
    @GET
    @Path("/header")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimpleMessageJson() {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("{\"simpleMessage\": \"This is a simple message\"}");
        bob.type(MediaType.APPLICATION_JSON);
        return bob.build();
    }

    /**
     * Dummy method that return a simple message as XML
     * @return simple message as XML
     */
    @GET
    @Path("/header")
    @Consumes(MediaType.TEXT_XML)
    @Produces(MediaType.TEXT_XML)
    public Response getSimpleMessageXML() {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("<simpleMessage>This is a simple message</simpleMessage>");
        bob.type(MediaType.TEXT_XML);
        return bob.build();
    }
    /**
     * Dummy method that return a simple message as Text
     * @return simple message as Text
     */
    @GET
    @Path("/header")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSimpleMessageText() {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("This is a simple message");
        bob.type(MediaType.TEXT_PLAIN);
        return bob.build();
    }

}