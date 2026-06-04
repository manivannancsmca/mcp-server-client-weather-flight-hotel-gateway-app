# ==========================================
# Stage 1: Build the application using JDK 21
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Copy only the pom.xml first to cache dependencies (speeds up future builds)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the thin executable JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Create the lightweight runtime image
# ==========================================
# Using eclipse-temurin alpine JRE for Java 21 (Only ~150MB total image size)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root system user for security hardening
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the compiled JAR file from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Optimizing JVM memory footprints for container environments
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:+ExitOnOutOfMemoryError", "-jar", "app.jar"]