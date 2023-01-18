package at.fhooe.sail.vis.soap.dynamic.environment;

import at.fhooe.sail.vis.main.IEnvService;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Dynamic Environment SOAP-Client
 */
public class Environment_SoapClient {
    /**
     * Interface for EnvironmentService
     */
    public IEnvService mSOAP;

    /**
     * Creates service and sets mSOAP variable
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public Environment_SoapClient() throws MalformedURLException, RemoteException {
        Service service = Service.create(
                new URL("http://localhost:8081/Environment_SoapServer?wsdl"),
                new QName("http://environment.dynamic.soap.vis.sail.fhooe.at/",
                        "Environment_SoapServerService"));
        mSOAP = service.getPort(IEnvService.class);
    }
}
