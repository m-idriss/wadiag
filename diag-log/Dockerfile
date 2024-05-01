### STAGE 1: Download ###
FROM curlimages/curl:8.2.1 AS download
ARG OTEL_AGENT_VERSION="1.33.1"
WORKDIR /downloads
RUN curl --silent --fail -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar" \
    -o "opentelemetry-javaagent.jar"

### STAGE 2: Build ###
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /opt/apt
COPY src ./src
COPY pom.xml .
# Build app
RUN mvn clean package -DskipTests
RUN mv target/*.jar app.jar

### STAGE 3: Final ###
FROM openjdk:21-slim
WORKDIR /opt/apt
VOLUME /tmp
COPY --from=build /opt/apt/app.jar /app.jar 
COPY --from=download /downloads/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

# Install curl
RUN apt-get update && apt-get install -y curl && apt-get clean

# Health check
HEALTHCHECK --interval=300s --timeout=10s --start-period=5s CMD sh -c 'if [ "$PORT" = "8443" ]; then curl -f https://localhost:$PORT/actuator/health -k; else curl -f http://localhost:$PORT/actuator/health; fi' || exit 1

# Specify default command for container startup
ENTRYPOINT ["java", \
    "-javaagent:/opentelemetry-javaagent.jar", \
    "-jar", "/app.jar" \
    ]
