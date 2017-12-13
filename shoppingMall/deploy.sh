#!/bin/sh

echo "stop tomcat service"

TOMCAT_NAME="${1}"

if [ ! -n "$TOMCAT_NAME" ]; then
 echo ">>>>>>>>>>>>NOT SET TOMCAT_NAME EXIT<<<<<"
 exit 1
fi

MODULE_NAME="${2}"

if [ ! -n "$MODULE_NAME" ]; then
 echo ">>>>>>>>>>>>NOT SET MODULE_NAME user $JOB_NAME<<<<<"
 MODULE_NAME=$JOB_NAME
fi

WAR_NAME="${3}"

if [ ! -n "$WAR_NAME" ]; then
  WAR_NAME='ROOT'

  echo "not set WAR_NAME use defult name ROOT"
fi



pid=`jps -lv |grep "$TOMCAT_NAME" | awk '{print $1}'`

if [ "$pid" == '' ] ; then
    /stock/$TOMCAT_NAME/bin/shutdown.sh
    sleep 20s
fi

pid=`jps -lv |grep "$TOMCAT_NAME" | awk '{print $1}'`

for i in $pid
do
	echo ">>>kill $TOMCAT_NAME pid : $i"
	kill -9 $i ; 
done 


if [ -f /stock/$TOMCAT_NAME/webapps/*.war ];
then

    bakUrl=/stock/backups/war/`date "+%Y%m%d"`
    bakPrefix=`date "+%H%M%S"`

    mkdir -p $bakUrl
    mv /stock/$TOMCAT_NAME/webapps/*.war "$bakUrl/$WAR_NAME.war-$bakPrefix"

fi


echo "run tomcat service"

rm -rf /stock/$TOMCAT_NAME/webapps/*
cp ./$MODULE_NAME/target/*.war /stock/$TOMCAT_NAME/webapps/$WAR_NAME.war
/stock/$TOMCAT_NAME/bin/startup.sh

echo "run tomcat service ok"
