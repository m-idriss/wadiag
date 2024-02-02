### STAGE 1: Build ###
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /opt/apt
COPY pom.xml .
# copy module diag-app
COPY diag-app/src ./diag-app/src
COPY diag-app/pom.xml ./diag-app
# Build app
RUN mvn -f diag-app/pom.xml clean install

### STAGE 2: Run ###
FROM openjdk:17.0.1-slim
WORKDIR /app
COPY --from=build /opt/apt/diag-app/target/*.jar ./app.jar

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

EXPOSE ${SPRING_PORT}

# Health check
HEALTHCHECK --interval=300s --timeout=10s CMD curl -f http://localhost:${SPRING_PORT}/actuator/health || exit 1

ENV KAFKA_BOOTSTRAP_SERVERS=diag-kafka:9092

ENTRYPOINT ["java", "-jar", "app.jar"]
