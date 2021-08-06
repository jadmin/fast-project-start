#! /bin/bash
java -Denv=dev -Dlaunch=local -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9998,suspend=n -jar ${appName}-web/target/${appName}.jar