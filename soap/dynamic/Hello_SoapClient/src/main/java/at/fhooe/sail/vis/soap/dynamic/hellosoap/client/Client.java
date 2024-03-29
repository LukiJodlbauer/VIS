package at.fhooe.sail.vis.soap.dynamic.hellosoap.client;

import at.fhooe.sail.vis.soap.dynamic.hellosoap.main.ISimpleInterface;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Client for testing dynamic SOAP-Server
 */
public class Client {
    /**
     * SimpleInterface for getting DummyData
     */
    ISimpleInterface mSOAP;

    /**
     * Creates service and sets mSOAP variable
     * @throws MalformedURLException
     */
    public Client() throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:8080/Hello_SoapServer?wsdl"),
                new QName("http://main.hellosoap.dynamic.soap.vis.sail.fhooe.at/",
                        "Hello_SoapServerService"));
        mSOAP = service.getPort(ISimpleInterface.class);

        System.out.println("server ---> " + mSOAP.saySomething());
        System.out.println("server ---> " + mSOAP.getData("Dummy-Data"));
    }
}
