package at.fhooe.sail.vis.main;

import java.rmi.RemoteException;

/**
 * Basic Rest Client-Class for testing Client and Server Communication.
 */
public class Main {
    /**
     * @param _args
     * Main entry point that calls multiple endpoint on the EnvironmentRestServer
     */
    public static void main(String[] _args) {
        try {
            var client = new EnvironmentRestClient();

            System.out.println("------------------");
            System.out.println("requestEnvironmentDataTypes:");
            var sensorTypes = client.requestEnvironmentDataTypes();
            System.out.println(String.join(", ", sensorTypes));


            System.out.println("\n------------------");
            System.out.println("requestEnvironmentData:");
            for (String sensor : sensorTypes) {
                var envData = client.requestEnvironmentData(sensor);
                System.out.println(envData);
            }


            System.out.println("\n------------------");
            System.out.println("requestAll:");
            var allEnvData = client.requestAll();
            for (EnvData data : allEnvData) {
                System.out.println(data);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
