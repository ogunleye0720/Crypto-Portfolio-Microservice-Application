# ========================
# Stage 1: Build with Maven
# ========================
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy source
COPY . .

# Replace missing parent with Spring Boot starter parent
RUN sed -i '/<parent>/,/<\/parent>/c\
<parent>\n\
    <groupId>org.springframework.boot</groupId>\n\
    <artifactId>spring-boot-starter-parent</artifactId>\n\
    <version>2.7.8</version>\n\
    <relativePath/>\n\
</parent>' pom.xml

# Inject Spring Cloud BOM *right before <build>*
RUN sed -i '/<build>/i\
  <dependencyManagement>\n\
    <dependencies>\n\
      <dependency>\n\
        <groupId>org.springframework.cloud</groupId>\n\
        <artifactId>spring-cloud-dependencies</artifactId>\n\
        <version>2021.0.5</version>\n\
        <type>pom</type>\n\
        <scope>import</scope>\n\
      </dependency>\n\
    </dependencies>\n\
  </dependencyManagement>' pom.xml

# Build the application (skip tests for CI/CD speed)
RUN mvn clean package -DskipTests

# ========================
# Stage 2: Runtime (slim)
# ========================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
