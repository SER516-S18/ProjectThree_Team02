@echo off
set ROOT_PATH=%~dp0

set LIB_PATH=%ROOT_PATH%lib

cd %ROOT_PATH%

echo Removing client.jar

del \f \Q client.jar

echo Starting build...

dir \s \B %ROOT_PATH%\src\*.java > sources.txt
javac -d . -cp %ROOT_PATH%\src;%LIB_PATH%\grizzly-framework-2.3.3.jar;%LIB_PATH%\grizzly-http-2.3.3.jar;%LIB_PATH%\grizzly-http-server-2.3.3.jar;%LIB_PATH%\grizzly-rcm-2.3.3.jar;%LIB_PATH%\javax.json-1.0.4.jar;%LIB_PATH%\javax.websocket-api-1.0.jar;%LIB_PATH%\jcommon-1.0.16.jar;%LIB_PATH%\jfreechart-1.0.13.jar;%LIB_PATH%\tyrus-client-1.1.jar;%LIB_PATH%\tyrus-container-grizzly-1.1.jar;%LIB_PATH%\tyrus-core-1.1.jar;%LIB_PATH%\tyrus-server-1.1.jar;%LIB_PATH%\tyrus-spi-1.1.jar;%LIB_PATH%\tyrus-websocket-core-1.1.jar @sources.txt

jar cfm client.jar %ROOT_PATH%\manifest\client_manifest.txt lib src client model server util
del \f \Q client
del \f \Q network
del \f \Q util

echo Build finished!