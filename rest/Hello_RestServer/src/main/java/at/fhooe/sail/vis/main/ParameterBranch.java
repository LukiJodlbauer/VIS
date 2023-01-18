package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/parameter")

public class ParameterBranch {
    @GET
    @Path("/url/content/{type}")
    @Produces(MediaType.TEXT_XML)
    public String getTypeXml(@PathParam("type") String type) {
        return "<type>"+type+"</type>";
    }
    @GET
    @Path("/url/variable")
    @Produces(MediaType.TEXT_XML)
    public String getQueryParamXml(@QueryParam("output") String output,@QueryParam("stuff") String stuff) {
        return "<params><output>"+output+"</output><stuff>"+stuff+"</stuff></params>";
    }
}
