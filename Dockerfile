FROM registry.access.redhat.com/ubi8/openjdk-11:latest
COPY build/libs/*.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-app-0.0.1-SNAPSHOT.jar"]