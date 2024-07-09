FROM openjdk:17

ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} backend.jar

ENTRYPOINT [ "java", "-jar", "backend.jar" ]

FROM ubuntu

WORKDIR ./src/main/resources

COPY application-db.yml ./src/main/resources/application-db.yml
COPY application.yml ./src/main/resources/application.yml
