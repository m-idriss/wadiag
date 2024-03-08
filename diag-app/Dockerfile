### STAGE 1: Build ###
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /opt/apt
COPY src ./src
COPY pom.xml .
# Build app
RUN mvn clean package -DskipTests

### STAGE 2: Run ###
FROM openjdk:21-slim
WORKDIR /opt/apt
VOLUME /tmp
COPY --from=build /opt/apt/target/*.jar diag-app.jar

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

# Health check
HEALTHCHECK --interval=300s --timeout=10s --start-period=10s CMD curl -f http://localhost:8081/actuator/health || exit 1

# Specify default command for container startup
CMD ["java", "-jar", "diag-app.jar"]