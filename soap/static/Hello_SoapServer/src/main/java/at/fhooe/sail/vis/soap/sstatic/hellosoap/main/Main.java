package at.fhooe.sail.vis.soap.sstatic.hellosoap.main;

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
		System.out.println("Hello static");

		Endpoint.publish("http://localhost:8080/Hello_SoapServer",
				new Hello_SoapServer());

		System.out.println("server up and running ...");
	}
}
