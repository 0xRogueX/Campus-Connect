# CampusConnect

A comprehensive microservices-based campus management platform built with Spring Boot and Spring Cloud.

## Overview

CampusConnect is designed to streamline campus operations by providing modular services for authentication, course management, attendance tracking, event scheduling, exam management, material sharing, profile management, result processing, and scheduling.

## Features

- **Authentication Service**: Secure user authentication with JWT tokens and OTP verification via email.
- **Course Service**: Manage courses, branches, and related data.
- **Attendance Service**: Track student attendance.
- **Events Service**: Organize and manage campus events.
- **Exam Service**: Handle exam scheduling and management.
- **Materials Service**: Share and manage study materials.
- **Profile Service**: User profile management.
- **Result Service**: Process and display exam results.
- **Scheduling Service**: Manage timetables and schedules.
- **API Gateway**: Centralized routing and load balancing for all services.

## Architecture

The application follows a microservices architecture with an API Gateway for routing requests to individual services. Services communicate via HTTP and use different databases as needed (PostgreSQL for auth, MongoDB for courses).

```
[API Gateway (Port 9000)]
    |
    +-- [Auth Service (Port 8080, PostgreSQL)]
    +-- [Course Service (Port 8070, MongoDB)]
    +-- [Events Service (Port 8060)]
    +-- [Exam Service (Port 8050)]
    +-- [Materials Service (Port 8081)]
    +-- [Profile Service (Port 8030)]
    +-- [Result Service (Port 8020)]
    +-- [Scheduling Service (Port 8010)]
    +-- [Attendance Service]
```

## Tech Stack

- **Backend**: Java 17/21, Spring Boot 3.x, Spring Cloud 2024.x
- **Databases**: PostgreSQL (Auth), MongoDB (Courses)
- **Security**: Spring Security, JWT
- **Email**: SMTP (Gmail)
- **API Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven
- **Gateway**: Spring Cloud Gateway MVC

## Prerequisites

- Java 17 or 21
- Maven 3.6+
- PostgreSQL 12+
- MongoDB 4.4+
- Gmail account for SMTP (or configure alternative)

## Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd campus-connect
   ```

2. **Configure Databases**:
   - Install and start PostgreSQL and MongoDB.
   - Create databases: `authdb` in PostgreSQL, `coursedb` in MongoDB.
   - Update connection details in `application.yml` files if needed.

3. **Configure Email**:
   - Update SMTP settings in `auth-service/src/main/resources/application.yml` with your email credentials.

4. **Build the services**:
   ```bash
   # Build each service
   cd auth-service && mvn clean install
   cd ../course-service && mvn clean install
   # Repeat for other services
   cd ../api-gateway && mvn clean install
   ```

## Running the Services

Start each service in separate terminals:

1. **Auth Service**:
   ```bash
   cd auth-service
   mvn spring-boot:run
   ```

2. **Course Service**:
   ```bash
   cd course-service
   mvn spring-boot:run
   ```

3. **API Gateway**:
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

4. **Other Services**: Similarly, navigate to each service directory and run `mvn spring-boot:run`.

Services will start on their respective ports as configured.

## API Documentation

Each service exposes API documentation via Swagger UI:

- Auth Service: http://localhost:8080/swagger-ui.html
- Course Service: http://localhost:8070/swagger-ui.html
- And so on for other services.

Access the API Gateway at http://localhost:9000 for routed endpoints.

## Testing

Run tests for each service:

```bash
cd <service-directory>
mvn test
```

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Commit changes.
4. Push to the branch.
5. Create a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please contact [dev.vivek.dadhaniya@gmail.com].
