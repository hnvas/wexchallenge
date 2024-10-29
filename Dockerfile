# build stage
FROM gradle:8.10.2-jdk21-alpine AS builder

WORKDIR /usr/app/

COPY . .

RUN gradle bootJar

# build runtime
FROM eclipse-temurin:21-jdk-alpine

COPY --from=builder /usr/app/build/libs/*.jar /opt/app/application.jar

CMD java -jar /opt/app/application.jar