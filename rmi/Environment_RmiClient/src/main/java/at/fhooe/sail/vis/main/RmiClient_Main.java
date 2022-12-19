package at.fhooe.sail.vis.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient_Main {
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
        } catch (Exception _e) {_e.printStackTrace();}
    }
}