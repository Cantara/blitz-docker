package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public abstract class ActivationGroup extends UnicastRemoteObject implements ActivationInstantiator {
    protected ActivationGroup(ActivationGroupID groupID) throws RemoteException { }
    public boolean inactiveObject(ActivationID id) throws ActivationException, UnknownObjectException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public abstract void activeObject(ActivationID id, Remote obj) throws ActivationException, UnknownObjectException, RemoteException;
    public static synchronized ActivationGroup createGroup(ActivationGroupID id, ActivationGroupDesc desc, long incarnation) throws ActivationException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static synchronized ActivationGroupID currentGroupID() { return null; }
    public static synchronized void setSystem(ActivationSystem system) throws ActivationException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static synchronized ActivationSystem getSystem() throws ActivationException {
        throw new ActivationException("activation not supported on this JVM");
    }
    protected void activeObject(ActivationID id, MarshalledObject<? extends Remote> mobj) throws ActivationException, UnknownObjectException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    protected void inactiveGroup() throws UnknownGroupException, RemoteException {
        throw new UnknownGroupException("activation not supported on this JVM");
    }
}
