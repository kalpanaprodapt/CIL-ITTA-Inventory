# syntax=docker/dockerfile:1.7
# inventory-facility-service - Spring Boot service for the Facility domain
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY inventory-common/pom.xml inventory-common/pom.xml
COPY inventory-facility-service/pom.xml inventory-facility-service/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -pl inventory-facility-service -am dependency:go-offline
COPY inventory-common inventory-common
COPY inventory-facility-service inventory-facility-service
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -pl inventory-facility-service -am -DskipTests package \
 && java -Djarmode=layertools -jar inventory-facility-service/target/inventory-facility-service-1.0.0-SNAPSHOT.jar extract --destination /layers

FROM eclipse-temurin:17-jre-alpine AS runtime
RUN addgroup -S -g 10001 app && adduser -S -u 10001 -G app app
WORKDIR /app
COPY --from=build /layers/dependencies/ ./
COPY --from=build /layers/spring-boot-loader/ ./
COPY --from=build /layers/snapshot-dependencies/ ./
COPY --from=build /layers/application/ ./
USER 10001:10001
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health/liveness || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
