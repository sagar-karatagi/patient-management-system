# ------------ Stage 1: Build the application ------------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle files first
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code (needed for protobuf generation)
COPY src ./src

# Build the application with layers enabled
RUN ./gradlew clean bootJar -x test --no-daemon && rm -rf ~/.gradle

# Extract layers from the built jar
RUN java -Djarmode=layertools -jar build/libs/*.jar extract

# ------------ Stage 2: Runtime with layers ------------
FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app

# Copy layers in order of least to most frequently changing
# This maximizes Docker layer caching benefits
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Expose ports
EXPOSE 4002 9001

# Use Spring Boot's JarLauncher for layered execution
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]