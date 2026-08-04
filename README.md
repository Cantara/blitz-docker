# blitz-docker
Docker Image running Blitz and ApacheRiver for Java Spaces

The image runs on Java 21 (LTS). That is deliberate: Jini/River installs a
`SecurityManager` at startup, and the SecurityManager was removed in JDK 24
(JEP 486). Java 21 is the newest LTS that can still run Blitz/River — the
image sets `-Djava.security.manager=allow` for you.

Build
-----
* `docker build -t cantara/blitz .`

River version and the Blitz installer location can be overridden:

* `docker build --build-arg RIVER_VERSION=2.2.2 --build-arg BLITZ_INSTALLER_URL=<url> -t cantara/blitz .`

To run
-----
* `docker run -it -p 8085:8085 -p 4160:4160 cantara/blitz`

Get a shell in a running instance
-----
* `docker exec -it <container> bash`

(The image no longer ships an SSH daemon — `docker exec` replaced it, and the
old image's hardcoded root password is gone. Blitz now runs as the non-root
`blitz` user.)
