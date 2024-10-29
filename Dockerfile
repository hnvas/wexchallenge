FROM eclipse-temurin:21-jdk-alpine

COPY build/libs/*.jar /usr/src/wex/wex-challenge.jar
WORKDIR /usr/src/wex
EXPOSE 8080
CMD ["java", "-jar", "wex-challenge.jar"]