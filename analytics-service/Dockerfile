# ------------ Stage 1: Build the application ------------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper and config files first for caching
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code (including protobuf etc.)
COPY src ./src

# Build the application with layered jars and custom name
RUN ./gradlew clean bootJar -x test --no-daemon && rm -rf ~/.gradle

# Extract layers from the built jar (using fixed name)
RUN java -Djarmode=layertools -jar build/libs/analytics-service.jar extract

# ------------ Stage 2: Runtime with layers ------------
FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

# Copy extracted layers in order for caching
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Expose ports your app listens on
EXPOSE 4002

# Run the app using Spring Boot's layered launcher
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
