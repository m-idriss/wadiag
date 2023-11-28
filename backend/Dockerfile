### STAGE 1: Build ###
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean install

### STAGE 2: Run ###
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /workspace/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
