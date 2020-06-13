#!/bin/bash

if [ -z "$1" ] ; then
   echo "password is empty."
   exit 1
fi

PASSWORD=$1

cp /home/xiaolongzuo/db_backup_blog.sql /home/xiaolongzuo/db_backup_blog.sql.bak
cp /home/xiaolongzuo/app_backup_blog.tar.gz /home/xiaolongzuo/app_backup_blog.tar.gz.bak

rm -f /home/xiaolongzuo/db_backup_blog.sql
rm -f /home/xiaolongzuo/app_backup_blog.tar.gz

mysqldump -uroot -p$PASSWORD blog > /home/xiaolongzuo/db_backup_blog.sql
tar -zcvf /home/xiaolongzuo/app_backup_blog.tar.gz /home/xiaolongzuo/zuoxiaolong/

chown xiaolongzuo.xiaolongzuo /home/xiaolongzuo/db_backup_blog.sql
chown xiaolongzuo.xiaolongzuo /home/xiaolongzuo/app_backup_blog.tar.gz

rm -f /home/xiaolongzuo/db_backup_blog.sql.bak
rm -f /home/xiaolongzuo/app_backup_blog.tar.gz.bak