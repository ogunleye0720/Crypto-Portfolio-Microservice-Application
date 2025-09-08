# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the whole project (parent + submodules)
COPY . .

# Debug: Check the directory structure and POM files
RUN echo "=== Directory structure ===" && \
    find . -name "pom.xml" -exec echo "Found POM: {}" \; && \
    echo "=== Root POM content ===" && \
    head -20 pom.xml && \
    echo "=== Discovery-server POM parent section ===" && \
    head -20 discovery-server/pom.xml

# Try different approaches based on the project structure
# Option 1: Build from root with specific reactor projects
RUN mvn clean install -pl discovery-server -am -DskipTests || \
    # Option 2: If option 1 fails, build everything
    mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the discovery-server JAR from builder stage
COPY --from=builder /app/discovery-server/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
