#!/bin/bash

cd /home/zuoxiaolong/personal-blog-webapp
git pull
sleep 5
mvn clean install -Dmaven.test.skip=true
rm -f /home/zuoxiaolong/personal-blog-webapp-server/personal-blog-webapp-server.jar
cp personal-blog-webapp-server/target/personal-blog-webapp-server.jar /home/zuoxiaolong/personal-blog-webapp-server/
cd personal-blog-webapp-site/target
unzip -qo zuoxiaolong.war -d /home/zuoxiaolong/personal-blog-webapp-site

ps -ef | grep 'personal-blog-webapp-server' | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
cd /home/zuoxiaolong/personal-blog-webapp-server/
nohup java -Xms256m -Xms256m -Dspring.config.location=classpath:application.yml,/home/zuoxiaolong/personal-blog-webapp-server/application-product.yml -jar personal-blog-webapp-server.jar >/home/zuoxiaolong/personal-blog-webapp-server/std.log 2>&1 &
echo "personal-blog-webapp-server started"
sleep 30
ps -ef | grep 'org.apache.catalina.startup.Bootstrap' | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
cd /home/zuoxiaolong/personal-blog-webapp-site/
/home/zuoxiaolong/apache-tomcat-8.0.21/bin/startup.sh
echo "personal-blog-webapp-site started"

