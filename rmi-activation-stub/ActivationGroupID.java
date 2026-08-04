package java.rmi.activation;
public class ActivationGroupID implements java.io.Serializable {
    private ActivationSystem system;
    public ActivationGroupID(ActivationSystem system) { this.system = system; }
    public ActivationSystem getSystem() { return system; }
}
