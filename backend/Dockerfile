FROM openjdk:17

WORKDIR /src/main/resources

ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /src/main/resources/backend.jar
COPY application-db.yml /src/main/resources/application-db.yml
COPY application.yml /src/main/resources/application.yml

ENTRYPOINT [ "java", "-jar", "/src/main/resources/backend.jar" ]