package at.fhooe.sail.vis.main;

import at.fhooe.sail.vis.socket.Environment_SocketClient;

/**
 * Basic Class for testing our C++ EnvironmentServer
 */
public class EnvironmentClient_Main {

	/**
	 * Entry point of application
	 *
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		IEnvService service = new Environment_SocketClient();
		while (true) {
			String[] sensors = service.requestEnvironmentDataTypes();
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
			try {
				Thread.sleep(1000);
			} catch (Exception _e) {
				_e.printStackTrace();
			}
		}
	}
}
