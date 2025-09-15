FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache dependency
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline || true
# Copy source code
COPY . .

# Build project và force jar name = app.jar
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests clean package && \
    cp $(ls -1 target/*.jar | grep -v original | head -n1) /app/app.jar

FROM eclipse-temurin:21-jre AS runner
# Healthcheck tool
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
# Non-root
RUN useradd -ms /bin/bash spring
USER spring
WORKDIR /app

COPY --from=build /app/app.jar /app/app.jar
# JVM for containers
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom -Dspring.jmx.enabled=false"
EXPOSE 8080

HEALTHCHECK --interval=15s --timeout=5s --start-period=30s --retries=5 \
  CMD curl -fsS http://localhost:8080/actuator/health >/dev/null || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
