# java.rmi.activation stub

Minimal source stubs for the `java.rmi.activation` package, which was removed
from the JDK in Java 17 (JEP 407). Apache River's `ServiceStarter` and JERI
runtime still link against these classes even when only non-activatable
services are used (as Blitz does), so without them River cannot start on
Java 17+.

The Docker build compiles these into `rmi-activation-stub.jar` and injects it
with:

    --patch-module=java.rmi=/home/blitz/rmi-activation-stub.jar
    --add-exports=java.rmi/java.rmi.activation=ALL-UNNAMED

Every method that would actually perform activation throws
`ActivationException` — the stubs satisfy class linking only. Activatable
services will not work; Blitz runs in non-activatable mode and never calls
them.
