# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the project
COPY . .

# Remove parent POM dependency and add Spring Boot parent directly
RUN sed -i '/<parent>/,/<\/parent>/c\
<parent>\
    <groupId>org.springframework.boot</groupId>\
    <artifactId>spring-boot-starter-parent</artifactId>\
    <version>2.7.8</version>\
    <relativePath\/>\
</parent>' pom.xml

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
