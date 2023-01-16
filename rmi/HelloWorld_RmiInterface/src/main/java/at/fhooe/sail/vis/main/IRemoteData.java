package at.fhooe.sail.vis.main;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Basic RMI Interface for testing Client Server method invocation
 */
public interface IRemoteData extends Remote {

    /**
     * Dummy-output
     * @throws RemoteException
     */
    void saySomething() throws RemoteException;
}

