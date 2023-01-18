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

@Path("/data")

public class DataBranch {

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

    private int[] generateRandomValues(int amount){
        Random rand = new Random();
        List<Integer> values = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            values.add(rand.nextInt(250));
        }
        return values.stream().mapToInt(i->i).toArray();
    }
}

@XmlRootElement(name="sensorDataList")
class SensorDatas{
    @XmlElement(name = "sensor")
    public List<EnvData> sensors = new ArrayList<>();

    public SensorDatas(){}

    @Override
    public String toString() {
        return "SensorDatas{" +
                "sensors=" + sensors +
                '}';
    }
}
