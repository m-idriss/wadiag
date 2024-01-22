### STAGE 1: Build ###
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean install

### STAGE 2: Run ###
FROM openjdk:17.0.1-slim
WORKDIR /app
COPY --from=build /workspace/target/*.jar ./app.jar

ENV KAFKA_BOOTSTRAP_SERVERS=diag-kafka:9092

ENTRYPOINT ["java", "-jar", "app.jar"]
