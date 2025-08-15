# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine
# Set the working directory
WORKDIR /app
# Copy the jar file
COPY target/mediscreen-0.0.1-SNAPSHOT.jar app.jar
# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]