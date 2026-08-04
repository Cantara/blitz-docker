package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
public interface ActivationInstantiator extends Remote {
    MarshalledObject<? extends Remote> newInstance(ActivationID id, ActivationDesc desc)
        throws ActivationException, java.rmi.RemoteException;
}
