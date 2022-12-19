package at.fhooe.sail.vis.main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteData extends Remote {
    public void saySomething() throws RemoteException;
}

