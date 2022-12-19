package at.fhooe.sail.vis.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main extends UnicastRemoteObject implements IRemoteData{
    public Main() throws RemoteException { super(); }
    public void saySomething(){  System.out.println("cookies"); }
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