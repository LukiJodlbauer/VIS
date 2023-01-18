package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

/**
 * Info Branch for accessing informatino about the available sensor on the REST Server
 */
@Path("/info")
public class InfoBranch {
    /**
     * GET method for the available sensors
     * @param output request format either JSON or XML
     * @return all available sensortypes in the requested format
     */
    @GET
    @Path("/sensortypes")
    @Produces({MediaType.TEXT_XML,MediaType.APPLICATION_JSON})
    public Response getTypeXml(@QueryParam("output") @DefaultValue("XML")String output) {
        Response.ResponseBuilder bob = Response.ok();
        Sensor[] sensors = new Sensor[]{new Sensor("air"),new Sensor("light"),new Sensor("noise")};
        //String[] sensors = new String[]{"light","air","noise"};
        if(output.equals("XML")){
           bob.type(MediaType.APPLICATION_XML);
       }
       else if (output.equals("JSON")){
           bob.type(MediaType.APPLICATION_JSON);
       }
        bob.entity(sensors);
        return bob.build();
    }
}

/**
 * Sensor Helper class for JAXB Parsing
 */
@XmlRootElement
class Sensor{
    /**
     * Name of the sensor
     */
    @XmlElement
    public String name;

    /**
     * Empty constructor for JAXB
     */
    public Sensor(){}

    /**
     * Constructor
     * @param name name of the sensor
     */
    public Sensor(String name){
        this.name = name;
    }

    /**
     * Converts object to string representation
     * @return String representation of sensor
     */
    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
}
