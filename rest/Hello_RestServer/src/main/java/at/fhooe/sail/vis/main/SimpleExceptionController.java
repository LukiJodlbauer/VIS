package at.fhooe.sail.vis.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

public class SimpleExceptionController implements ExceptionMapper<Throwable> {
    @Context
    private UriInfo uriInfo;
    @Context
    private HttpServletRequest mRequest;
    @Override
    public Response toResponse(Throwable _e) {
        String bob = "<!DOCTYPE html> \n" +
                "<html lang=\"en\"> \n" +
                "<head><title>Error Page</title></head>" +
                "<body><h1>Error:" + _e.getMessage() + "</h1>" + "</body>" +
                "</html>";
        Response.ResponseBuilder bobReturn = Response.ok();
        bobReturn.entity(bob);
        return bobReturn.build();
    }
}
