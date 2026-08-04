package java.rmi.activation;
public class ActivateFailedException extends java.rmi.RemoteException {
    public ActivateFailedException(String s) { super(s); }
    public ActivateFailedException(String s, Exception ex) { super(s, ex); }
}
