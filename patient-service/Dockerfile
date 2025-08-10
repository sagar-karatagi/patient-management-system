# ------------ Stage 1: Build the application ------------
FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

# Copy Gradle config first to leverage cache
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies (leveraging Docker cache)
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon && rm -rf ~/.gradle

# ------------ Stage 2: Run the application ------------
FROM eclipse-temurin:21-jre-jammy as runtime

# Create non-root user
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

WORKDIR /app

# Copy built jar from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Change permissions to non-root user
RUN chown -R appuser:appgroup /app

# Environment variables (can be overridden at runtime)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db \
    SPRING_DATASOURCE_USERNAME=admin_user \
    SPRING_DATASOURCE_PASSWORD=password \
    SPRING_JPA_HIBERNATE_DDL_AUTO=update \
    SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect

# Expose app port
EXPOSE 4000

# Run as non-root user
USER appuser

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]