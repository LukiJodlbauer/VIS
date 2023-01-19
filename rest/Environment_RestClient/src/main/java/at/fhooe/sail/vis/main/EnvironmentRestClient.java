package at.fhooe.sail.vis.main;


import jakarta.xml.bind.*;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Scanner;

/**
 * Basic Rest Client-Class for testing Client and Server Communication.
 */
public class EnvironmentRestClient implements IEnvService {
    /**
     * Base URL
     */
    private static final String mURL = "http://localhost:8080/EnvironmentRestServer/";
    /**
     * Charset
     */
    private static final String mCHARSET = "UTF-8";

    @Override
    public String[] requestEnvironmentDataTypes() throws RemoteException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Sensor.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            um.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
            um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

            String query = String.format("output=%s",
                    URLEncoder.encode("JSON", mCHARSET));
            String response = executeGetRequest(mURL + "info/sensortypes", query);


            StreamSource reader = new StreamSource(new StringReader(response));
            Collection<Sensor> sensors = (Collection<Sensor>) um.unmarshal(reader, Sensor.class).getValue();
            return sensors.stream().map(x -> x.name).toArray(String[]::new);
        } catch (UnsupportedEncodingException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EnvData requestEnvironmentData(String _type) throws RemoteException{
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EnvData.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            um.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
            um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

            String query = String.format("output=%s",
                    URLEncoder.encode("JSON", mCHARSET));
            String response = executeGetRequest(mURL + "data/" + _type, query);

            StreamSource reader = new StreamSource(new StringReader(response));
            return um.unmarshal(reader, EnvData.class).getValue();
        } catch (UnsupportedEncodingException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EnvData[] requestAll() throws RemoteException{
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SensorDatas.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            um.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
            //um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

            String query = String.format("output=%s",
                    URLEncoder.encode("XML", mCHARSET));
            String response = executeGetRequest(mURL + "data/ALL", query);
            // StreamSource json = new StreamSource(new StringReader(response));
            SensorDatas sensorDatas = (SensorDatas) um.unmarshal(new StringReader(response));
            EnvData[] result = new EnvData[sensorDatas.sensors.size()];
            result = sensorDatas.sensors.toArray(result);
            return result;
        } catch (UnsupportedEncodingException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Baisc Http get call method
     * @param targetURL target URL for the call
     * @param query query parameters for the call
     * @return response body
     */
    public String executeGetRequest(String targetURL, String query){
        try {
            URLConnection connection = new URL(targetURL + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Basic http get call without query parameters
     * @param targetURL target URL for the call
     * @return response body
     */
    public String executeGetRequest(String targetURL){
        try {
            URLConnection connection = new URL(targetURL).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);
                return responseBody;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}