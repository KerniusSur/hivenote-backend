FROM openjdk:17-ea-jdk-slim
MAINTAINER KerniusSur
VOLUME /tmp
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
COPY target/*.jar app.jar
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 8080
