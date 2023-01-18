package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@Path("/info")
public class InfoBranch {
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
@XmlRootElement
class Sensor{
    @XmlElement
    public String name;

    public Sensor(){}

    public Sensor(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
}
