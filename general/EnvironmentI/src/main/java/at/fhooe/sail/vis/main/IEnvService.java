package at.fhooe.sail.vis.main;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Basic interface for testing our EnvironmentClient and -Server.
 */
public interface IEnvService extends Remote {
	/**
	 * Requests the sensor types of our environment server.
	 *
	 * @return the sensor types as String[]
	 */
	String[]  requestEnvironmentDataTypes() throws RemoteException;

	/**
	 * Requests the data of the given sensor type.
	 *
	 * @param _type the sensor type for which the data should
	 *              be requested
	 * @return the data of the given sensor as EnvData Object
	 */
	EnvData   requestEnvironmentData(String _type) throws RemoteException;

	/**
	 * Requests the data of all sensor types.
	 *
	 * @return the data of all sensor types as an EnvData[]
	 */
	EnvData[] requestAll () throws RemoteException;
}
