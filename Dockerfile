# Blitz JavaSpaces + Apache River on Java 21 (LTS).
#
# Running River/Blitz on a modern JDK needs three accommodations, all applied
# here and verified end-to-end (write/read/take from a client) on Java 21:
#  - SecurityManager was terminally deprecated (JEP 411): JDK 18-23 need
#    -Djava.security.manager=allow. It was removed in JDK 24 (JEP 486), so
#    Java 21 LTS is the ceiling for River/Blitz.
#  - java.rmi.activation was removed in JDK 17 (JEP 407), but River's
#    ServiceStarter and JERI runtime still link against it. A stub jar
#    (see rmi-activation-stub/) is patched into the java.rmi module.
#  - java.rmi.server.useCodebaseOnly defaults to true since 7u21, which
#    breaks Jini dynamic proxy downloading; it is switched back to false.

ARG RIVER_VERSION=2.2.2
# Blitz binaries moved from GitHub downloads to Google Code, whose archive
# still serves them (verified). Override if you host the installer elsewhere.
ARG BLITZ_INSTALLER_URL=https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/blitz-javaspaces/installer_pj_2_1_7.jar

## Stage 1: download River, run the Blitz installer, build the activation stub
FROM azul/zulu-openjdk:21 AS installer
ARG RIVER_VERSION
ARG BLITZ_INSTALLER_URL

RUN apt-get update \
    && apt-get install -y --no-install-recommends wget unzip \
    && rm -rf /var/lib/apt/lists/*

RUN useradd --create-home blitz

## Build the java.rmi.activation stub (removed in JDK 17, still linked by River)
COPY rmi-activation-stub /tmp/rmi-activation-stub/java/rmi/activation
RUN cd /tmp/rmi-activation-stub \
    && rm -f java/rmi/activation/README.md \
    && javac --patch-module java.rmi=. -d classes java/rmi/activation/*.java \
    && jar cf /home/blitz/rmi-activation-stub.jar -C classes . \
    && chown blitz:blitz /home/blitz/rmi-activation-stub.jar

USER blitz
WORKDIR /home/blitz

## Install Apache River
RUN wget -q -O apache-river-${RIVER_VERSION}-bin.zip \
        "https://archive.apache.org/dist/river/river-${RIVER_VERSION}/apache-river-${RIVER_VERSION}-bin.zip" \
    && unzip -q apache-river-${RIVER_VERSION}-bin.zip \
    && rm apache-river-${RIVER_VERSION}-bin.zip

## Install Blitz
RUN wget -q -O blitz-installer.jar "${BLITZ_INSTALLER_URL}" \
    && java -Dblitz.nocheck=true -jar blitz-installer.jar \
        /home/blitz/apache-river-${RIVER_VERSION} /home/blitz/blitz 8085 \
    && chmod +x /home/blitz/blitz/*.sh \
    && rm blitz-installer.jar

## The installer hardcodes the build-time JAVA_HOME into the start scripts;
## make them use the runtime image's JAVA_HOME instead.
RUN sed -i 's|^JAVA_HOME=.*|JAVA_HOME="${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")}"|' \
        /home/blitz/blitz/*.sh

## Stage 2: runtime image
FROM azul/zulu-openjdk:21-jre

LABEL org.opencontainers.image.title="blitz-docker" \
      org.opencontainers.image.description="Docker image running Blitz JavaSpaces and Apache River" \
      org.opencontainers.image.source="https://github.com/Cantara/blitz-docker" \
      org.opencontainers.image.licenses="Apache-2.0"

RUN useradd --create-home blitz

COPY --from=installer --chown=blitz:blitz /home/blitz /home/blitz

# See header comment: SecurityManager opt-in, activation stub, and Jini
# dynamic code downloading — all required for River/Blitz on Java 21.
ENV JAVA_TOOL_OPTIONS="-Djava.security.manager=allow \
--patch-module=java.rmi=/home/blitz/rmi-activation-stub.jar \
--add-exports=java.rmi/java.rmi.activation=ALL-UNNAMED \
--add-exports=java.base/sun.security.provider=ALL-UNNAMED \
-Djava.rmi.server.useCodebaseOnly=false"

USER blitz
WORKDIR /home/blitz/blitz

# 4160 Jini discovery, 8081 River class server, 8085 Blitz class server
EXPOSE 4160 8081 8085

CMD ["/bin/bash", "/home/blitz/blitz/blitz.sh"]
