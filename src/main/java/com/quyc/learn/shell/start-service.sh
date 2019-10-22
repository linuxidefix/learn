#!/bin/bash


echo -n 请等待:
count=0
while true
do    
     PIDS=$(ps -ef|grep java|grep learn-1.0-SNAPSHOT.jar|grep -v grep|awk '{print $2}')
     if [ "$PIDS" == "" ]; then
       nohup java -jar learn-1.0-SNAPSHOT.jar &
       break
      else 
       kill  $PIDS
       let count=count+1
       sleep 1
       echo -n "$count"秒  
      fi
done
