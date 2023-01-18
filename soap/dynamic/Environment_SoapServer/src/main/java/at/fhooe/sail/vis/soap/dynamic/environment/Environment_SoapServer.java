package at.fhooe.sail.vis.soap.dynamic.environment;

import at.fhooe.sail.vis.main.EnvData;
import at.fhooe.sail.vis.main.IEnvService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.*;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Environment SOAP-Server
 */
@WebService(endpointInterface =
        "at.fhooe.sail.vis.main.IEnvService")
public class Environment_SoapServer implements IEnvService {
    /**
     * Weather API Url
     */
    private static final String _WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={type}&units=metric&APPID=86adbae393bbc32a60da344d9629bf35";

    /**
     * @return available cities
     * @throws RemoteException
     */
    @WebMethod
    @Override
    public String[] requestEnvironmentDataTypes() throws RemoteException {
        return new String[] {"Wien,at", "Muenchen,de", "Hamburg,de"};
    }

    /**
     * @param _type the city for which the data should
     *              be requested
     * @return EnvData object
     * @throws RemoteException
     */
    @Override
    public EnvData requestEnvironmentData(String _type) throws RemoteException {
        EnvData result = new EnvData();
        result.setSensorName(_type);
        result.setTimestamp(System.currentTimeMillis());

        try {
            URL url = new URL(_WEATHER_URL.replace("{type}", _type));
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            Scanner s = new Scanner(http.getInputStream(), StandardCharsets.UTF_8);
            s.useDelimiter("\\z");
            String pageContent = s.next();
            s.close();

            JAXBContext jaxbContext = JAXBContext.newInstance(WeatherResponse.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();

            um.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
            um.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

            StreamSource json = new StreamSource(new StringReader(pageContent));
            var weatherResponse = (WeatherResponse) um.unmarshal(json, WeatherResponse.class).getValue();

            result.setValues(new int[] {
                    (int)weatherResponse.getTemperature(),
                    weatherResponse.getHumidity(),
                    weatherResponse.getPressure()});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * @return EnvData-Array of all available cities.
     * @throws RemoteException
     */
    @Override
    public EnvData[] requestAll() throws RemoteException {
        var result = new ArrayList<EnvData>();

        String[] sensors = requestEnvironmentDataTypes();
        for (String sensor : sensors) {
            result.add(requestEnvironmentData(sensor));
        }

        return result.toArray(new EnvData[0]);
    }
}