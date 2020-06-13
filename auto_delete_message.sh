#!/bin/bash

if [ -z "$1" ] ; then
   echo "password is empty."
   exit 1
fi

PASSWORD=$1

mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages group by visitor_ip having count(*) > 10) a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages group by visitor_ip having count(*)>1 and count(distinct(message))=1) a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select message,visitor_ip from messages group by message,visitor_ip having count(*)>1) a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages where message like '%&lt;button%') a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages where message like '%&lt;script%') a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages where message like '%<button>%') a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages where message like '%&lt;input%') a);"
mysql -uroot -p$PASSWORD blog -e "delete from messages where visitor_ip in (select a.visitor_ip from (select visitor_ip from messages where message like '%<input%') a);"