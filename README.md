# blitz-docker
Docker Image running Blitz and ApacheRiver for Java Spaces

The image runs on Java 21 (LTS), which required three accommodations — all
applied by the image and verified end-to-end (a client performing
write/read/take against the running space):

* **SecurityManager**: terminally deprecated in JDK 17 (JEP 411), removed in
  JDK 24 (JEP 486). Java 21 is the newest LTS that can run River/Blitz; the
  image sets `-Djava.security.manager=allow`.
* **`java.rmi.activation`**: removed in JDK 17 (JEP 407), but River still
  links against it even for non-activatable services. The image patches a
  small stub (see `rmi-activation-stub/`) into the `java.rmi` module.
* **`java.rmi.server.useCodebaseOnly=false`**: the JDK default changed to
  `true` in 7u21, which silently breaks Jini dynamic proxy downloading.

Build
-----
* `docker build -t cantara/blitz .`

River version and the Blitz installer location can be overridden. The
installer now defaults to the Google Code archive (the historical
`github.com/downloads` URL is dead):

* `docker build --build-arg RIVER_VERSION=2.2.2 --build-arg BLITZ_INSTALLER_URL=<url> -t cantara/blitz .`

To run
-----
* `docker run -it -p 8085:8085 -p 8081:8081 -p 4160:4160 cantara/blitz`

Get a shell in a running instance
-----
* `docker exec -it <container> bash`

(The image no longer ships an SSH daemon — `docker exec` replaced it, and the
old image's hardcoded root password is gone. Blitz now runs as the non-root
`blitz` user.)

Connecting a client on Java 17+
-----
Client JVMs linking River's JERI runtime need the same activation stub and
flags; see [blitz-client-basic](https://github.com/Cantara/blitz-client-basic):

    --patch-module=java.rmi=rmi-activation-stub.jar
    --add-exports=java.rmi/java.rmi.activation=ALL-UNNAMED
    -Djava.rmi.server.useCodebaseOnly=false
