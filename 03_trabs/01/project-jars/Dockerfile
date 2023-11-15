# Use a base image with Java
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /usr

# Copy the JAR file into the container at /usr/app
COPY MarkImageApp-1.0-jar-with-dependencies.jar .

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "MarkImageApp-1.0-jar-with-dependencies.jar"]