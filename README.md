# tmw-home-test-ori-blanka

## Overview
`tmw-home-test-ori-blanka` is a Spring Boot application designed to build a weather service that ingests weather data, stores it in a database, and provides APIs for fetching weather forecasts, summarizing weather data, and managing batch metadata.


## Prerequisites
- Java 17
- Maven 3.9.9

## Building the Project
To build the project, you can use the provided Maven wrapper scripts.

### Using Maven Wrapper
```sh
./mvnw clean install
## Project Structure

Configuration
The application can be configured using the application.properties file located in src/main/resources.

Dependencies
The project uses the following key dependencies:

Spring Boot Starter Parent 3.4.0
Lombok 1.18.36
MapStruct 1.6.3
MapStruct Lombok 0.2.0
For a full list of dependencies, refer to the pom.xml file.

License
This project is licensed under the Apache License 2.0. See the LICENSE file for details.


to build this:
    1. docker-compose build
    2. docker-compose up

Deploy to Render.com:
Sign up and create a new web service on Render.com.
Connect your repository.
Configure the build and start commands:
Build Command: docker-compose build
Start Command: docker-compose up
Add environment variables if needed.
Deploy the service.