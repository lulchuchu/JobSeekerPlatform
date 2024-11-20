# Use the Amazon Corretto 17 JDK as the base image
FROM amazoncorretto:17-alpine

# Create a group and user for running the application
RUN addgroup -S spring && adduser -S spring -G spring

# Set the user to run the application
USER spring:spring

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY target/JobSeekerPlatform-0.0.1-SNAPSHOT.jar /app/JobSeekerPlatform.jar

# Specify the entry point for the application
ENTRYPOINT ["java", "-jar", "/app/JobSeekerPlatform.jar"]