package at.fhooe.sail.vis.main;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI Client class for Client-Server Connection Sensor data
 */
public class RmiClient_Main {
    /**
     * Main method that connects to the registry and request data periodically
     * @param args
     */
    public static void main(String[] args) {
        try {
            String adr = "RmiServer";
            Registry reg = LocateRegistry.getRegistry();
            IEnvService rmi = (IEnvService) reg.lookup(adr);
            while (true) {
                String[] sensors = rmi.requestEnvironmentDataTypes();
                for (String sensor : sensors) {
                    EnvData dataO = rmi.requestEnvironmentData(sensor);
                    System.out.print(dataO);
                    System.out.println();
                    System.out.println("*****************************");
                }
                System.out.println();
                System.out.println();
                EnvData[] dataOs = rmi.requestAll();
                for (EnvData dataO : dataOs) {
                    System.out.println(dataO);
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception _e) {
                    _e.printStackTrace();
                }
            }
            //System.out.println("The server date is: " + date);
        } catch (NotBoundException | RemoteException | NullPointerException _e) {
            System.out.print("Connection is closed");
        }

    }
}