package at.fhooe.sail.vis.soap.sstatic.environment;

import at.fhooe.sail.vis.soap.generated.EnvData;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.stream.Collectors;

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
			String[] sensors = service.requestEnvironmentDataTypes().toArray(new String[0]);
			for(String sensor: sensors){
				System.out.println(sensor);
			}

			for (String sensor : sensors) {
				EnvData dataO = service.requestEnvironmentData(sensor);
				System.out.println(dataO.getSensorName());
				System.out.println(dataO.getTimestamp());
				System.out.println(dataO.getValues()
						.stream()
						.map(Object::toString)
						.collect(Collectors.joining(", ")));
				System.out.println();
				System.out.println("*****************************");
			}

			System.out.println();
			System.out.println();
			EnvData[] dataOs = service.requestAll().toArray(new EnvData[0]);
			for (EnvData dataO : dataOs) {
				System.out.println(dataO.getSensorName());
				System.out.println(dataO.getTimestamp());
				System.out.println(dataO.getValues()
						.stream()
						.map(Object::toString)
						.collect(Collectors.joining(", ")));
				System.out.println("*****************************");
			}
		} catch (MalformedURLException | RemoteException e) {
			throw new RuntimeException(e);
		}
	}
}
