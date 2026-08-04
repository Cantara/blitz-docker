package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface ActivationMonitor extends Remote {
    void inactiveObject(ActivationID id) throws UnknownObjectException, RemoteException;
    void activeObject(ActivationID id, MarshalledObject<? extends Remote> obj) throws UnknownObjectException, RemoteException;
    void inactiveGroup(ActivationGroupID id, long incarnation) throws UnknownGroupException, RemoteException;
}
