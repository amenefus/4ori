# Use a Maven image with a compatible Java runtime for building the application
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files into the container
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src

# Ensure mvnw has execute permissions
RUN chmod +x mvnw

# Build the Maven project
RUN ./mvnw clean install

# Use a lightweight JDK image for running the application
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]