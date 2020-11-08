#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64

HTTP_SERVER_CODE=`curl -I -o /dev/null -s -w %{http_code} http://localhost:8888/tag/getHotTags`
if [ "x$HTTP_SERVER_CODE" != "x200" ];then
	ps -ef | grep "personal-blog-webapp-server.jar" | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
    sleep 2
    cd /home/zuoxiaolong/personal-blog-webapp-server/
    nohup $JAVA_HOME/bin/java -Xms128m -Xms128m -Dspring.config.location=classpath:application.yml,/home/zuoxiaolong/personal-blog-webapp-server/application-product.yml -jar personal-blog-webapp-server.jar >/home/zuoxiaolong/personal-blog-webapp-server/std.log 2>&1 &
    CURRENT=`date`
    echo "$CURRENT restart." >> /home/zuoxiaolong/personal-blog-webapp-server/logs/restart.log
    sleep 30
fi

HTTP_CODE=`curl -w %{http_code} http://localhost:8080/personal-blog-webapp-site`
if [ "x$HTTP_CODE" == "x302" ];then
	exit 0
fi
ps -ef | grep "org.apache.catalina.startup.Bootstrap" | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
cd /home/zuoxiaolong/personal-blog-webapp-site/
/home/zuoxiaolong/apache-tomcat-8.0.21/bin/startup.sh
CURRENT=`date`
echo "$CURRENT restart." >> /home/zuoxiaolong/apache-tomcat-8.0.21/logs/restart.log