package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
public class ActivationID implements java.io.Serializable {
    private ActivationInstantiator activator;
    public ActivationID(ActivationInstantiator activator) { this.activator = activator; }
    public Remote activate(boolean force) throws ActivationException, UnknownObjectException, java.rmi.RemoteException {
        throw new ActivationException("activation not supported on this JVM");
    }
}
