package java.rmi.activation;
import java.rmi.MarshalledObject;
import java.util.Properties;
public final class ActivationGroupDesc implements java.io.Serializable {
    private String className;
    private String location;
    private MarshalledObject<?> data;
    private CommandEnvironment env;
    private Properties props;
    public ActivationGroupDesc(Properties overrides, CommandEnvironment cmd) { this(null, null, null, overrides, cmd); }
    public ActivationGroupDesc(String className, String location, MarshalledObject<?> data, Properties overrides, CommandEnvironment cmd) {
        this.className = className; this.location = location; this.data = data; this.props = overrides; this.env = cmd;
    }
    public String getClassName() { return className; }
    public String getLocation() { return location; }
    public MarshalledObject<?> getData() { return data; }
    public Properties getPropertyOverrides() { return props; }
    public CommandEnvironment getCommandEnvironment() { return env; }
    public static class CommandEnvironment implements java.io.Serializable {
        private String command;
        private String[] options;
        public CommandEnvironment(String cmdpath, String[] argv) { this.command = cmdpath; this.options = argv == null ? new String[0] : argv.clone(); }
        public String getCommandPath() { return command; }
        public String[] getCommandOptions() { return options.clone(); }
    }
}
