package at.fhooe.sail.vis.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

public class Main {
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