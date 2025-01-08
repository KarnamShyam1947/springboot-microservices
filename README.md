# Book Ordering Microservice

[![Spring Boot 3.1.1](https://img.shields.io/badge/Spring%20Boot-3.1.1-green.svg?logo=spring-boot)](https://spring.io/blog/2023/06/22/spring-boot-3-1-1-available-now)
[![Spring Security 5](https://img.shields.io/badge/Spring%20Security-5-green.svg?logo=spring)](https://docs.spring.io/spring-security/reference/index.html)
[![Spring Cloud 2022](https://img.shields.io/badge/Spring%20Cloud-v2022.0.3-green.svg?logo=spring)](https://docs.spring.io/spring-security/reference/index.html)
[![PostgreSQL 14](https://img.shields.io/badge/mysql-8-blue?style=flat&logo=mysql&logoColor=blue
)](https://www.postgresql.org/)
[![PostgreSQL 14](https://img.shields.io/badge/PostgreSQL-v14-blue.svg?logo=postgresql)](https://www.postgresql.org/)
![Static Badge](https://img.shields.io/badge/nongodb-3-green?style=flat&logo=mongodb&logoColor=green)
[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven 4](https://img.shields.io/badge/Maven-3.9.9-orange.svg?logo=maven)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Overview

This project is a **Book Ordering Microservice** built using Spring Boot. It demonstrates various design patterns and includes features such as authentication using Keycloak, API Gateway, service discovery, and OpenFeign for communication between microservices.

## Features

- **Microservices Architecture**: Each component of the application is designed as an independent microservice.
- **Authentication**: Secure user authentication and authorization using [Keycloak](https://www.keycloak.org/).
- **API Gateway**: A gateway to route requests to the appropriate microservices.
- **Service Discovery**: Utilizes Spring Cloud Netflix Eureka for service discovery.
- **OpenFeign**: Simplifies HTTP client creation for inter-service communication.
- **Design Patterns**: Incorporates various design patterns to enhance code structure and reusability.

## Architecture

The architecture of the system is based on microservices that interact with each other through well-defined APIs. Below is a high-level overview of the architecture:


## Technologies Used

- Spring Boot
- Spring Cloud
- Keycloak
- Eureka
- OpenFeign
- Maven

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Keycloak Server (for authentication)
- Docker (optional)

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/KarnamShyam1947/springboot-microservices.git
   cd springboot-microservices
   ```

2. Set up Keycloak:

    Follow the Keycloak documentation to set up your Keycloak server.
    Create a realm and configure the necessary clients and roles.

3. Build Project
    ```bash
    mvn clean install
    ```

## Configuration
Update the `application.yml` or `application.properties` files with the necessary configuration for your environment, including Keycloak details, database configuration, etc.

## Usage

Once the microservices are up and running, you can interact with the API Gateway to place orders, manage users, and perform other operations.
