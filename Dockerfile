# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the project files into the container
COPY pom.xml .
COPY src src

# Build the Maven project
RUN mvn clean package -DskipTests

# Expose the port the app runs on
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]