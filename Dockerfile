# Blitz JavaSpaces + Apache River on Java 21 (LTS).
#
# Java 21 is deliberate: Jini/River installs a SecurityManager at startup, and
# the SecurityManager was removed in JDK 24 (JEP 486). Java 21 is the newest
# LTS that can still run Blitz/River, with -Djava.security.manager=allow.

ARG RIVER_VERSION=2.2.2
ARG BLITZ_INSTALLER_URL=https://github.com/downloads/dancres/blitzjavaspaces/installer_pj_2_1_7.jar

## Stage 1: download River and run the Blitz installer
FROM azul/zulu-openjdk:21 AS installer
ARG RIVER_VERSION
ARG BLITZ_INSTALLER_URL

RUN apt-get update \
    && apt-get install -y --no-install-recommends wget unzip \
    && rm -rf /var/lib/apt/lists/*

RUN useradd --create-home blitz
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

## Stage 2: runtime image
FROM azul/zulu-openjdk:21-jre

LABEL org.opencontainers.image.title="blitz-docker" \
      org.opencontainers.image.description="Docker image running Blitz JavaSpaces and Apache River" \
      org.opencontainers.image.source="https://github.com/Cantara/blitz-docker" \
      org.opencontainers.image.licenses="Apache-2.0"

RUN useradd --create-home blitz

COPY --from=installer --chown=blitz:blitz /home/blitz /home/blitz

# River/Blitz install a SecurityManager; JDK 18+ requires opting in at launch.
ENV JAVA_TOOL_OPTIONS="-Djava.security.manager=allow"

USER blitz
WORKDIR /home/blitz/blitz

EXPOSE 8085 4160

CMD ["/bin/bash", "/home/blitz/blitz/blitz.sh"]
