package java.rmi.activation;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface ActivationSystem extends Remote {
    int SYSTEM_PORT = 1098;
    ActivationID registerObject(ActivationDesc desc) throws ActivationException, UnknownGroupException, RemoteException;
    void unregisterObject(ActivationID id) throws ActivationException, UnknownObjectException, RemoteException;
    ActivationGroupID registerGroup(ActivationGroupDesc desc) throws ActivationException, RemoteException;
    ActivationMonitor activeGroup(ActivationGroupID id, ActivationInstantiator group, long incarnation) throws UnknownGroupException, ActivationException, RemoteException;
    void unregisterGroup(ActivationGroupID id) throws ActivationException, UnknownGroupException, RemoteException;
    void shutdown() throws RemoteException;
    ActivationDesc setActivationDesc(ActivationID id, ActivationDesc desc) throws ActivationException, UnknownObjectException, UnknownGroupException, RemoteException;
    ActivationGroupDesc setActivationGroupDesc(ActivationGroupID id, ActivationGroupDesc desc) throws ActivationException, UnknownGroupException, RemoteException;
    ActivationDesc getActivationDesc(ActivationID id) throws ActivationException, UnknownObjectException, RemoteException;
    ActivationGroupDesc getActivationGroupDesc(ActivationGroupID id) throws ActivationException, UnknownGroupException, RemoteException;
}
