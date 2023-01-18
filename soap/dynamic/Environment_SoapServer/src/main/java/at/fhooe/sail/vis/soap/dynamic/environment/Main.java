package at.fhooe.sail.vis.soap.dynamic.environment;

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
		System.out.println("Environment SoapServer dynamic");

		Endpoint.publish("http://localhost:8081/Environment_SoapServer",
				new Environment_SoapServer());

		System.out.println("server up and running ...");
	}
}
