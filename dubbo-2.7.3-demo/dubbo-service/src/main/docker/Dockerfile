FROM 192.168.1.52/system_containers/oraclejdk:8u192

MAINTAINER wangjinshan jin.shan@tenxcloud.com

ENV PROFILE         test
ENV ZK_HOST         192.168.1.6
ENV ZK_PORT         2181
ENV LOG_LEVEL INFO

ADD dubbo-service-1.0-SNAPSHOT.jar /app/app.jar

WORKDIR "/app"

EXPOSE 8000

ENTRYPOINT ["java", "-Xms256m", "-Xmx1g", \
            "-XX:+UnlockExperimentalVMOptions", \
            "-XX:+UseCGroupMemoryLimitForHeap", \
            "-Dspring.profiles.active=${PROFILE}",\
            "-Dzk.host=${ZK_HOST}",\
            "-Dzk.port=${ZK_PORT}",\
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "/app/app.jar"]
