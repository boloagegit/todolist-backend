FROM openjdk:11-alpine
EXPOSE 5505
ARG JAR_FILE=build/libs/todolist-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]