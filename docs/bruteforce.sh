#!/bin/bash

COOKIE="JSESSIONID=$1"

echo $COOKIE
ACCOUNT=100123
while [ $ACCOUNT -lt 100301 ]; do
	STATUS=`curl --cookie $COOKIE --write-out "%{http_code}" --silent --output /dev/null http://localhost:8080/accounts/$ACCOUNT`
	if [ $STATUS -eq 200 ];
	then
		echo "Found account $ACCOUNT"
	fi
	let ACCOUNT=ACCOUNT+1 
done