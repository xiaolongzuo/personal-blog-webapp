#!/bin/bash

cd /home/zuoxiaolong/personal-blog-webapp
git pull
sleep 5
mvn clean package -Dmaven.test.skip=true
cp personal-blog-webapp-server/target/personal-blog-webapp-server /home/zuoxiaolong/personal-blog-webapp-server/
cd personal-blog-webapp-site/target
unzip -o zuoxiaolong.war -d /home/zuoxiaolong/personal-blog-webapp-site

ps -ef | grep 'personal-blog-webapp-server' | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
su - zuoxiaolong -c "nohup java -Xms256m -Xms256m -Dspring.config.location=classpath:application.yml,/home/zuoxiaolong/personal-blog-webapp-server/application-product.yml -jar personal-blog-webapp-server.jar >/home/zuoxiaolong/personal-blog-webapp-server/std.log 2>&1 &"
sleep 30
ps -ef | grep 'personal-blog-webapp-site' | grep -v 'grep' | awk '{print "kill -9 " $2}' | sh
sleep 2
su - zuoxiaolong -c "/home/zuoxiaolong/apache-tomcat-8.0.21/bin/startup.sh"

