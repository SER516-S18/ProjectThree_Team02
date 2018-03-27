@echo off
set ROOT_PATH=%~dp0

set LIB_PATH=%ROOT_PATH%lib

cd %ROOT_PATH%

echo Removing server.jar and client.jar

del /f /Q server.jar
del /f /Q client.jar

echo Starting build...

javac -d . -cp "%ROOT_PATH%src;%LIB_PATH%\kryo-4.0.1.jar;%LIB_PATH%\kryonet-2.22.0-RC1.jar;%LIB_PATH%\jfreechart-1.0.13.jar;%LIB_PATH%\jcommon-1.0.16.jar;%LIB_PATH%\minlog-1.3.0.jar;%LIB_PATH%\objenesis-2.1.jar;%LIB_PATH%\reflectasm-1.10.1-shaded.jar" %ROOT_PATH%src\server\ServerMain.java

jar cfm server.jar %ROOT_PATH%\manifest\server_manifest.txt lib src server network util
del /f /Q server

javac -d . -cp "%ROOT_PATH%src;%LIB_PATH%\kryo-4.0.1.jar;%LIB_PATH%\kryonet-2.22.0-RC1.jar;%LIB_PATH%\jfreechart-1.0.13.jar;%LIB_PATH%\jcommon-1.0.16.jar;%LIB_PATH%\minlog-1.3.0.jar;%LIB_PATH%\objenesis-2.1.jar;%LIB_PATH%\reflectasm-1.10.1-shaded.jar" %ROOT_PATH%src\client\ClientMain.java

jar cfm client.jar %ROOT_PATH%\manifest\client_manifest.txt lib src client network util
del /f /Q client
del /f /Q network
del /f /Q util

echo Build finished!