package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

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
		System.out.println("Hello SoapServer dynamic");

		Endpoint.publish("http://localhost:8080/Hello_SoapServer",
				new Hello_SoapServer());

		System.out.println("server up and running ...");
	}
}
