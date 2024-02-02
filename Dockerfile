### STAGE 1: Build ###
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /opt/apt
COPY pom.xml ./wadiag
# copy module diag-app
COPY diag-app/src ./wadiag/diag-app/src
COPY diag-app/pom.xml ./wadiag/diag-app
# Build app
RUN mvn -f wadiag/diag-app/pom.xml clean install

### STAGE 2: Run ###
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /opt/apt/wadiag/diag-app/target/*.jar ./app.jar

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

EXPOSE ${SPRING_PORT}

# Health check
HEALTHCHECK --interval=300s --timeout=10s CMD curl -f http://localhost:${SPRING_PORT}/actuator/health || exit 1

ENV KAFKA_BOOTSTRAP_SERVERS=diag-kafka:9092

ENTRYPOINT ["java", "-jar", "app.jar"]
