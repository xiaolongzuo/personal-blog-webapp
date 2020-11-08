#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64

MYSQL_NUM=`ps -ef | grep mysql | grep -v 'grep' | wc -l`
if [ "x$MYSQL_NUM" != "x2" ];then
	service mysqld start
fi

NGINX_NUM=`ps -ef | grep nginx | grep -v 'grep' | wc -l`
if [ "x$NGINX_NUM" != "x2" ];then
	/usr/sbin/nginx
fi

HTTP_SERVER_CODE=`curl -I -o /dev/null -s -w %{http_code} http://localhost:8888/tag/getHotTags`
if [ "$HTTP_CODE" != "200" ];then
	ps -ef | grep "personal-blog-webapp-server.jar" | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
    sleep 2
    su - zuoxiaolong -c "java -Xms128m -Xms128m -Dspring.config.location=classpath:application.yml,/home/zuoxiaolong/personal-blog-webapp-server/application-product.yml -jar personal-blog-webapp-server.jar"
    CURRENT=`date`
    echo "$CURRENT restart." >> /home/zuoxiaolong/personal-blog-webapp-server/logs/restart.log
    chown zuoxiaolong.zuoxiaolong /home/zuoxiaolong/personal-blog-webapp-server/logs/restart.log
fi

HTTP_CODE=`curl -w %{http_code} http://localhost:8080/personal-blog-webapp-site`
if [ "$HTTP_CODE" == "302" ];then
	exit 0
fi
ps -ef | grep "org.apache.catalina.startup.Bootstrap" | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
su - zuoxiaolong -c "/home/zuoxiaolong/apache-tomcat-8.0.21/bin/startup.sh"
CURRENT=`date`
echo "$CURRENT restart." >> /home/zuoxiaolong/apache-tomcat-8.0.21/logs/restart.log
chown zuoxiaolong.zuoxiaolong /home/zuoxiaolong/apache-tomcat-8.0.21/logs/restart.log