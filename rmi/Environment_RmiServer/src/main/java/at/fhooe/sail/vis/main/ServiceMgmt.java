package at.fhooe.sail.vis.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServiceMgmt {
    private static final String _registry = "RmiServer";
    public static void main(String[] args) {

        System.out.println("Menü\n");
        System.out.println("start -> RMI Service gets started\n");
        System.out.println("stop -> RMI Service gets stopped\n");
        System.out.println("quit -> Service Mgmt shuts down\n");
        Scanner scanner = new Scanner(System.in);
        // Process rmiServer = null;
        try {
            Registry reg =
                    LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            RmiServer_Main rmi = null;
            while (true) {
                System.out.println("Enter command");
                String line = scanner.nextLine();
                try {
                    if (line.equals("start")) {
                        // Start extra gradle deamon and kill it with gradle script, kill does not work
                        // ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", "gradle rmi:Environment_RmiServer:run");
                        // processBuilder.directory(new File("../../"));
                        // rmiServer = processBuilder.start();
                        // System.out.println("PID"+rmiServer.pid());
                        rmi = new RmiServer_Main();
                        reg.rebind(_registry, rmi);
                        System.out.println("Server is waiting for queries ...");
                    }
                    if (line.equals("stop")) {
                        try {
                            reg.unbind(_registry);
                            if(!UnicastRemoteObject.unexportObject(rmi, true)){
                                System.out.println("Something went wrong at unexportObject method");
                            }

                        } catch (NotBoundException e) {
                            System.out.println("RMI Server not started");
                        }
                    }
                    if (line.equals("quit")) {
                        try {
                            reg.unbind(_registry);
                            if(!UnicastRemoteObject.unexportObject(rmi, true)){
                                System.out.println("Something went wrong at unexportObject method");
                            }                        } catch (NotBoundException e) {
                            System.out.println("RMI Server not started");
                        }
                        break;
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}