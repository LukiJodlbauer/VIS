package at.fhooe.sail.vis.environmentservice.rmi;

import at.fhooe.sail.vis.main.EnvData;
import at.fhooe.sail.vis.main.IEnvService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RmiClient class
 */
public class RmiClient {

    /**
     * Gets EnvData from RMI Server
     * @return EnvData Array
     * @throws Exception
     */
    public static EnvData[] getEnvData() throws Exception {
        String adr = "RmiServer";
        Registry reg = LocateRegistry.getRegistry();
        IEnvService rmi = (IEnvService) reg.lookup(adr);
        return rmi.requestAll();
    }
}