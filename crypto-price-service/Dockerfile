# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .

# Replace the parent POM with Spring Boot starter parent
RUN sed -i '/<parent>/,/<\/parent>/c\
<parent>\
    <groupId>org.springframework.boot</groupId>\
    <artifactId>spring-boot-starter-parent</artifactId>\
    <version>2.7.8</version>\
    <relativePath/>\
</parent>' pom.xml

RUN mvn clean package -DskipTests

# Stage 2: Runtime (secure, small base)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
