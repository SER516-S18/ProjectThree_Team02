#!/bin/sh

SCRIPT_PATH=$(readlink -f "$0")
ROOT_PATH=$(dirname "$SCRIPT_PATH")

LIB_PATH="$ROOT_PATH/lib"

echo
echo "\033[0;93mWorking dir: $ROOT_PATH"
echo

cd $ROOT_PATH
rm client.jar

echo
echo "Starting build..."
echo

echo
echo "\033[0;32mCompiling Client"
echo "\033[0;31m"

find "$ROOT_PATH/src" -name *.java > sources.txt

javac -d . -cp ".:$ROOT_PATH/src:$LIB_PATH/grizzly-framework-2.3.3.jar:$LIB_PATH/grizzly-http-2.3.3.jar:$LIB_PATH/grizzly-http-server-2.3.3.jar:$LIB_PATH/grizzly-rcm-2.3.3.jar:$LIB_PATH/javax.json-1.0.4.jar:$LIB_PATH/javax.websocket-api-1.0.jar:$LIB_PATH/jcommon-1.0.16.jar:$LIB_PATH/jfreechart-1.0.13.jar:$LIB_PATH/tyrus-client-1.1.jar:$LIB_PATH/tyrus-container-grizzly-1.1.jar:$LIB_PATH/tyrus-core-1.1.jar:$LIB_PATH/tyrus-server-1.1.jar:$LIB_PATH/tyrus-spi-1.1.jar:$LIB_PATH/tyrus-websocket-core-1.1.jar" @sources.txt

jar cfm client.jar ./manifest/client_manifest.txt lib src client util server model
rm -rf client
rm -rf model
rm -rf sources.txt

echo
echo "\033[0;32mCompile SUCCESS!"
echo
echo "\033[0;93mNext:"
echo "\033[0;93mUse 'java -jar client.jar' to start client"