package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
public abstract class Activatable extends RemoteServer {
    protected Activatable(String location, MarshalledObject<?> data, boolean restart, int port) throws ActivationException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    protected Activatable(ActivationID id, int port) throws RemoteException { }
    protected ActivationID getID() { return null; }
    public static Remote register(ActivationDesc desc) throws UnknownGroupException, ActivationException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static boolean inactive(ActivationID id) throws UnknownObjectException, ActivationException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static void unregister(ActivationID id) throws UnknownObjectException, ActivationException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static ActivationID exportObject(Remote obj, String location, MarshalledObject<?> data, boolean restart, int port) throws ActivationException, RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
    public static Remote exportObject(Remote obj, ActivationID id, int port) throws RemoteException {
        throw new RemoteException("activation not supported on this JVM");
    }
}
