package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Parameter Branch endpoints for displaying used parameters
 */
@Path("/parameter")
public class ParameterBranch {
    /**
     * Test method for using path parameters
     * @param type dummy path parameter
     * @return representation of dummy path parameter
     */
    @GET
    @Path("/url/content/{type}")
    @Produces(MediaType.TEXT_XML)
    public Response getTypeXml(@PathParam("type") String type) {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("<type>\"+type+\"</type>");
        bob.type(MediaType.TEXT_XML);
        return bob.build();
    }

    /**
     * Test method for using query parameters
     * @param output dummy query parameter
     * @param stuff dummy query parameter
     * @return representation of dummy query parameters
     */
    @GET
    @Path("/url/variable")
    @Produces(MediaType.TEXT_XML)
    public Response getQueryParamXml(@QueryParam("output") String output,@QueryParam("stuff") String stuff) {
        Response.ResponseBuilder bob = Response.ok();
        bob.entity("<params><output>\"output\"</output><stuff>\"stuff\"</stuff></params>");
        bob.type(MediaType.TEXT_XML);
        return bob.build();
    }
}
