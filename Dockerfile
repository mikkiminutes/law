# Use the official OpenJDK image as a base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]