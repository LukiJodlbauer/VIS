package at.fhooe.sail.vis.main;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Data Branch endpoints
 */
@Path("/data")
public class DataBranch {
    /**
     * This methods return the requested sensor in the requested format
     * @param sensor requested sensor
     * @param output requested format either JSON or XML
     * @return the requested sensor
     */
    @GET
    @Path("{sensor}")
    @Produces({MediaType.TEXT_XML,MediaType.APPLICATION_JSON})
    public Response getData(@PathParam("sensor") String sensor, @QueryParam("output") @DefaultValue("XML") String output){
        var result = new EnvData();
        result.setSensorName(sensor);
        long timestamp = System.currentTimeMillis() / 1000;
        result.setTimestamp(timestamp);

        result.setValues(generateRandomValues(1));
        Response.ResponseBuilder bob = Response.ok();
        if(output.equals("XML")){
            bob.type(MediaType.APPLICATION_XML);
        }
        else {
            bob.type(MediaType.APPLICATION_JSON);
        }
        bob.entity(result);
        return bob.build();
    }
    /**
     * This methods return all available sensor in the requested format
     * @param output requested format either JSON or XML
     * @return the all available sensor
     */
    @GET
    @Path("/ALL")
    @Produces({MediaType.TEXT_XML,MediaType.APPLICATION_JSON})
    public Response getAllData(@QueryParam("output") @DefaultValue("XML")String output){
        String[] sensors = new String[]{"air","light","noise"};
        SensorDatas result = new SensorDatas();
        for (String sensor : sensors) {
            var sensorData = new EnvData();
            sensorData.setSensorName(sensor);
            long timestamp = System.currentTimeMillis() / 1000;
            sensorData.setTimestamp(timestamp);

            sensorData.setValues(generateRandomValues(1));
            result.sensors.add(sensorData);
        }
        Response.ResponseBuilder bob = Response.ok();
        if(output.equals("XML")){
            bob.type(MediaType.APPLICATION_XML);
        }
        else if (output.equals("JSON")){
            bob.type(MediaType.APPLICATION_JSON);
        }
        bob.entity(result);
        return bob.build();
    }

    /**
     * This method generates dummy sensor values
     * @param amount amount of values to generate
     * @return generated value
     */
    private int[] generateRandomValues(int amount){
        Random rand = new Random();
        List<Integer> values = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            values.add(rand.nextInt(250));
        }
        return values.stream().mapToInt(i->i).toArray();
    }
}

/**
 * Helper Class for JAXB
 */
@XmlRootElement(name="sensorDataList")
class SensorDatas{
    /**
     * List of all available sensors
     */
    @XmlElement(name = "sensor")
    public List<EnvData> sensors = new ArrayList<>();

    /**
     * Empty constructor for JAXB
     */
    public SensorDatas(){}
    /**
     * Converts object to string representation
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "SensorDatas{" +
                "sensors=" + sensors +
                '}';
    }
}
