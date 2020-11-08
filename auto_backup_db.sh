#!/bin/bash

if [ -z "$1" ] ; then
   echo "password is empty."
   exit 1
fi

PASSWORD=$1

cp /home/zuoxiaolong/backup/db_backup_blog.sql /home/zuoxiaolong/backup/db_backup_blog.sql.bak
cp /home/zuoxiaolong/backup/app_backup_blog.tar.gz /home/zuoxiaolong/backup/app_backup_blog.tar.gz.bak

rm -f /home/zuoxiaolong/backup/db_backup_blog.sql
rm -f /home/zuoxiaolong/backup/app_backup_blog.tar.gz

mysqldump -uroot -p$PASSWORD blog > /home/zuoxiaolong/backup/db_backup_blog.sql
tar -zcvf /home/zuoxiaolong/backup/app_backup_blog.tar.gz /home/zuoxiaolong/personal-blog-webapp-site/

chown zuoxiaolong.zuoxiaolong /home/zuoxiaolong/backup/db_backup_blog.sql
chown zuoxiaolong.zuoxiaolong /home/zuoxiaolong/backup/app_backup_blog.tar.gz

rm -f /home/zuoxiaolong/backup/db_backup_blog.sql.bak
rm -f /home/zuoxiaolong/backup/app_backup_blog.tar.gz.bak