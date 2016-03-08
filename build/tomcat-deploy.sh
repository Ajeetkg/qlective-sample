#!/bin/bash

# This is template and changes are required to make it deploy on anyone's local machine
# Template is not for production/staging machine.

HIGHLIGHTER="[*******************************************************] -----> "

#Set the tomcat home
TOMCAT_HOME=/Users/agupta2/Documents/Softwares/apache-tomcat-8.0.27    # Change this.

#App name
APP_NAME=sample

#Set the Workspace
WORKSPACE=/Users/agupta2/Documents/ajeetgupta/git/sample  #Change this.

cd ${WORKSPACE}/
echo $HIGHLIGHTER "Build the "${APP_NAME}" application"
mvn clean install -Dmaven.skip.test=true;

echo $HIGHLIGHTER "Remove the existing app from the tomcat webapps"
rm -rf  ${TOMCAT_HOME}/webapps/${APP_NAME}-0.0.1-SNAPSHOT*

echo $HIGHLIGHTER "Copy the latest application to the tomcat webapps"
cp ${WORKSPACE}/target/${APP_NAME}-0.0.1-SNAPSHOT.war ${TOMCAT_HOME}/webapps/
cd ${TOMCAT_HOME}/bin
rm -rf ${TOMCAT_HOME}/logs/*

echo $HIGHLIGHTER "Kill the existing java application"
pgrep java | xargs kill -9

echo $HIGHLIGHTER "Shutdown the tomcat - Double test to ensure that tomcat is off"
${TOMCAT_HOME}/bin/shutdown.sh

echo $HIGHLIGHTER "Start the tomcat"
${TOMCAT_HOME}/bin/startup.sh

echo $HIGHLIGHTER"Open the application in the browser"
open "http://127.0.0.1:8080/"${APP_NAME}"-0.0.1-SNAPSHOT/swagger/index.html"