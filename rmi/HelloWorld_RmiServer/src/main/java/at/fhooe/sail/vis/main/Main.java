package at.fhooe.sail.vis.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Basic RMI Server class for Client-Server communication
 */
public class Main extends UnicastRemoteObject implements IRemoteData{
    /**
     * Default constructor
     * @throws RemoteException
     */
    public Main() throws RemoteException { super(); }

    /**
     * Implementation of the saySomething method from Interface
     */
    public void saySomething(){  System.out.println("cookies"); }

    /**
     * Main method that creates and binds the registry
     * @param args
     */
    public static void main(String[] args) {
        try {
            Main ds = new Main();
            Registry reg =
                    LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            reg.rebind("Main", ds);
            System.out.println("Server is waiting for queries ...");
        } catch (Exception _e) { _e.printStackTrace(); }
    }
}