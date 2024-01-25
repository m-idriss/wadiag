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

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s CMD curl -f http://localhost:8080/actuator/health || exit 1

ENV KAFKA_BOOTSTRAP_SERVERS=diag-kafka:9092

ENTRYPOINT ["java", "-jar", "app.jar"]
