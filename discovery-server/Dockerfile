# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the whole project (parent + submodules)
COPY . .

# Build only the discovery-server JAR
RUN mvn -pl discovery-server clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the discovery-server JAR from builder stage
COPY --from=builder /app/discovery-server/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
