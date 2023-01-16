package at.fhooe.sail.vis.soap.sstatic.hellosoap.soapclient;

import at.fhooe.sail.vis.soap.generated.HelloSoapServer;
import at.fhooe.sail.vis.soap.generated.HelloSoapServerService;
import jakarta.xml.ws.Endpoint;

/**
 * SOAP dynamic project.
 */
public class Main {

	/**
	 * Entry point of application
	 *
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("Hello SoapClient static");

		HelloSoapServerService srv = new HelloSoapServerService();
		HelloSoapServer soap = srv.getHelloSoapServerPort();
		System.out.println("server ---> " + soap.saySomething());
		System.out.println("server ---> " + soap.getData("Dummy-Data"));
	}
}
