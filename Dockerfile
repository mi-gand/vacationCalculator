FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/vacationCalculator.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]