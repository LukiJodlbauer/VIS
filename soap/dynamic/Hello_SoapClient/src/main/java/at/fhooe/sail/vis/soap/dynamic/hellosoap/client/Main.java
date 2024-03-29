package at.fhooe.sail.vis.soap.dynamic.hellosoap.client;

import java.net.MalformedURLException;

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
		System.out.println("Hello SoapClient dynamic");

		try {
			Client client = new Client();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
