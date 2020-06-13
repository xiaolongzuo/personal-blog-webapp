#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/jdk8

MYSQL_NUM=`ps -ef | grep mysql | grep -v 'grep' | wc -l`
if [ "x$MYSQL_NUM" != "x2" ];then
	/etc/init.d/mysql restart
fi

HTTP_CODE=`curl -w %{http_code} http://localhost:8080/zuoxiaolong`
if [ "$HTTP_CODE" == "302" ];then
	exit 0
fi
ps -ef | grep java | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
su - xiaolongzuo -c "/app/apache-tomcat-8.0.21/bin/startup.sh"
CURRENT=`date`
echo "$CURRENT restart." >> /home/xiaolongzuo/logs/restart.log
chown xiaolongzuo.xiaolongzuo /home/xiaolongzuo/logs/restart.log