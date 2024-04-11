FROM openjdk:21-jdk-slim-bullseye
MAINTAINER KerniusSur
VOLUME /tmp
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
COPY target/*.jar app.jar
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 8080 9092
