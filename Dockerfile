# Multi-stage build to optimize image size
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Copy Maven settings file
COPY maven-setting.xml /root/.m2/settings.xml

# Build the app with retry mechanism
RUN mvn clean package -DskipTests -B || \
    (sleep 10 && mvn clean package -DskipTests -B) || \
    (sleep 20 && mvn clean package -DskipTests -B)

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Install wget for healthcheck
RUN apk add --no-cache wget

WORKDIR /app

# Copy JAR file from build stage
COPY --from=build /app/target/TrendistaShop.jar /app/TrendistaShop.jar

# Expose ports
EXPOSE 8080 9093

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "/app/TrendistaShop.jar"]