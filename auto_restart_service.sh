#!/bin/bash

MYSQL_NUM=`ps -ef | grep mysql | grep -v 'grep' | wc -l`
if [ "x$MYSQL_NUM" != "x2" ];then
	service mysqld start
fi

NGINX_NUM=`ps -ef | grep nginx | grep -v 'grep' | wc -l`
if [ "x$NGINX_NUM" != "x2" ];then
	/usr/sbin/nginx
fi