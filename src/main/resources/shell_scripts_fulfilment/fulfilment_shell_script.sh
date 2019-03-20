#!/bin/sh
# This is a comment!

#FILE=config.properties
FILE=/home/aq4/code/fulfilment-testng-framework/src/main/resources/shell_scripts_fulfilment/config.properties

APPLICATION_PATH=$(grep -i 'celect_fulfilment_path' $FILE  | cut -f2 -d'=')

echo "$APPLICATION_PATH"

cd $APPLICATION_PATH

mvn clean test -DenvType=qa -Dsuite=MasterSuite


STATUS=$?
if [ $STATUS -eq 0 ]; then
echo "Deployment Successful"
else
echo "Deployment Failed"
fi
