#!/bin/sh
# Maven Wrapper script
# Auto-downloads Maven if not available

set -e

MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    DIST_URL=$(grep 'distributionUrl' "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

MAVEN_HOME="${HOME}/.m2/wrapper/dists/apache-maven-3.9.6"
MAVEN_CMD="${MAVEN_HOME}/bin/mvn"

if [ ! -f "$MAVEN_CMD" ]; then
    echo "Downloading Maven..."
    mkdir -p "$MAVEN_HOME"
    TMPFILE=$(mktemp /tmp/maven-XXXXXX.zip)
    curl -sSL "$DIST_URL" -o "$TMPFILE"
    unzip -q -o "$TMPFILE" -d "${HOME}/.m2/wrapper/dists/"
    rm "$TMPFILE"
    # Move extracted contents
    EXTRACTED_DIR=$(find "${HOME}/.m2/wrapper/dists/" -maxdepth 1 -name "apache-maven-3.9.6" -type d | head -1)
    if [ -z "$EXTRACTED_DIR" ]; then
        EXTRACTED_DIR=$(find "${HOME}/.m2/wrapper/dists/" -maxdepth 1 -name "apache-maven-*" -type d | head -1)
        if [ -n "$EXTRACTED_DIR" ] && [ "$EXTRACTED_DIR" != "$MAVEN_HOME" ]; then
            rm -rf "$MAVEN_HOME"
            mv "$EXTRACTED_DIR" "$MAVEN_HOME"
        fi
    fi
fi

exec "$MAVEN_CMD" "$@"
