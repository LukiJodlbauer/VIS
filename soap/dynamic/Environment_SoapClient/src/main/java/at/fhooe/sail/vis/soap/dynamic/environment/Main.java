package at.fhooe.sail.vis.soap.dynamic.environment;

import at.fhooe.sail.vis.main.EnvData;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

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
		System.out.println("Environment SoapClient dynamic");

		Environment_SoapClient client = null;
		try {
			client = new Environment_SoapClient();
			var service = client.mSOAP;

			System.out.println("Sensors: ");
			String[] sensors = service.requestEnvironmentDataTypes();
			for(String sensor: sensors){
				System.out.println(sensor);
			}

			for (String sensor : sensors) {
				EnvData dataO = service.requestEnvironmentData(sensor);
				System.out.print(dataO);
				System.out.println();
				System.out.println("*****************************");
			}

			System.out.println();
			System.out.println();
			EnvData[] dataOs = service.requestAll();
			for (EnvData dataO : dataOs) {
				System.out.println(dataO);
			}
		} catch (MalformedURLException | RemoteException e) {
			throw new RuntimeException(e);
		}
	}
}
