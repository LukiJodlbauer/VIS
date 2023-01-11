package at.fhooe.sail.vis.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * Basic RMI Client-Class for testing Client and Server Communication.
 */
public class Main {
    /**
     *
     * @param _args
     * Main entry point that starts the RMI Connection and calls the saySomething method
     */
    public static void main(String[] _args) {
        try {
            String adr = "Main";
            Registry reg = LocateRegistry.getRegistry();
            IRemoteData dateobject = (IRemoteData)reg.lookup(adr);
            dateobject.saySomething();
            //System.out.println("The server date is: " + date);
        } catch (Exception _e) {_e.printStackTrace();}
    }
}