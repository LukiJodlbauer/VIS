package at.fhooe.sail.vis.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Simple Exception Controller for displaying error messages
 */
public class SimpleExceptionController implements ExceptionMapper<Throwable> {
    /**
     * Simple method that show the thrown error as an html page
     * @param _e the exception to map to a response.
     * @return  an response that represents the error
     */
    @Override
    public Response toResponse(Throwable _e) {
        String bob = "<!DOCTYPE html> \n" +
                "<html lang=\"en\"> \n" +
                "<head><title>Error Page</title></head>" +
                "<body><h1>Error:" + _e.getMessage() + "</h1> </br> <p>"+_e.getCause().toString()+"</p>" + "</body>" +
                "</html>";
        Response.ResponseBuilder bobReturn = Response.ok();
        bobReturn.type(MediaType.TEXT_HTML);
        bobReturn.entity(bob);
        return bobReturn.build();
    }
}
