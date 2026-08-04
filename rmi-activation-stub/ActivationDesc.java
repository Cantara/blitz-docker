package java.rmi.activation;
import java.rmi.MarshalledObject;
public final class ActivationDesc implements java.io.Serializable {
    private ActivationGroupID groupID;
    private String className;
    private String location;
    private MarshalledObject<?> data;
    private boolean restart;
    public ActivationDesc(String className, String location, MarshalledObject<?> data) throws ActivationException {
        this(ActivationGroup.currentGroupID(), className, location, data, false);
    }
    public ActivationDesc(String className, String location, MarshalledObject<?> data, boolean restart) throws ActivationException {
        this(ActivationGroup.currentGroupID(), className, location, data, restart);
    }
    public ActivationDesc(ActivationGroupID groupID, String className, String location, MarshalledObject<?> data) {
        this(groupID, className, location, data, false);
    }
    public ActivationDesc(ActivationGroupID groupID, String className, String location, MarshalledObject<?> data, boolean restart) {
        this.groupID = groupID; this.className = className; this.location = location; this.data = data; this.restart = restart;
    }
    public ActivationGroupID getGroupID() { return groupID; }
    public String getClassName() { return className; }
    public String getLocation() { return location; }
    public MarshalledObject<?> getData() { return data; }
    public boolean getRestartMode() { return restart; }
}
