# First stage: Build the application using Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files into the container
COPY pom.xml mvnw ./
COPY src src

# Ensure mvnw has execute permissions
RUN chmod +x mvnw

# Build the Maven project
RUN ./mvnw clean install

# Second stage: Create a lightweight image to run the application
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]