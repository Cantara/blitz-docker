package java.rmi.activation;
public class ActivationException extends Exception {
    public Throwable detail;
    public ActivationException() {}
    public ActivationException(String s) { super(s); }
    public ActivationException(String s, Throwable cause) { super(s); detail = cause; }
    public Throwable getCause() { return detail; }
}
