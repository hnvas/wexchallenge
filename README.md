# Wexchagellenge

![Build Status](https://github.com/hnvas/wexchallenge/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/gh/hnvas/wexchallenge/branch/main/graph/badge.svg)](https://codecov.io/gh/hnvas/wexchallenge)


## Technologies Used
- **Java**
- **Gradle**
- **Spring Boot**
- **Swagger/OpenAPI**
- **Docker**
- **PostgreSQL**
- **Flyway**
- **Testcontainers**

## Project Structure
```bash
src
├── main
│    ├── Application # Use cases and application services
│    ├── Domain # Domain entities
│    └── Infrastructure # Infrastructure components
│      ├── Api # API documentation, controllers, and exception handlers
│      ├── Client # External API clients
│      ├── Config # Application configuration
│      ├── Gateway # Multisource data management
│      ├── Monitoring # Health check and metrics
│      ├── Persistence # Database access layer
├── test # Test classes
      ├── Application # Use cases and application services tests
      ├── Configuration # Test container configuration and test types annotations
      ├── Domain # Domain entities tests
      ├── Infrastructure # Infrastructure components tests
      
```

## API Endpoints

### Exchange API
- **GET /exchanges/{id}**
  - **Description**: Retrieve exchange rates for a given purchase ID.
  - **Parameters**:
    - `id` (Path Variable): ID of the purchase (required)
    - `country` (Query Parameter): Country name (optional)
    - `currency` (Query Parameter): Currency name (optional)
  - **Responses**:
    - `200`: Successful response with exchange rates.
    - `422`: Invalid input data.
    - `404`: Purchase not found.
    - `500`: Internal server error.

### Purchase API
- **POST /purchases**
  - **Description**: Create a new purchase.
  - **Parameters**:
    - `description` (Request Body): Purchase description (required)
    - `purchaseDate` (Request Body): Purchase date (required)
    - `amount` (Request Body): Purchase amount (required)
  - **Responses**:
    - `201`: Purchase created successfully.
    - `422`: Invalid input data.
    - `500`: Internal server error.

## Running Tests

### Prerequisites

- Ensure you have **Java 21** installed on your machine.

### Steps:

To run the tests, use the following command:
   ```sh
   ./gradlew test
   ```

## Running the Application

### Prerequisites

- Ensure you have **Docker** and **Docker Compose** installed on your machine.

### Steps

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/hnvas/wexchallenge.git
   cd wexchallenge
   ```

2. **Build and Run with Docker Compose:**
   This command will build the Docker image for the Java application and start both the PostgreSQL database and the application.
   ```sh
   docker-compose up -d
   ```

3. **Access the Application:**
   Once the containers are up and running, you can access the application at:
   ```sh
   http://localhost:8080
   ```

4. **Access API Documentation:**
   The Swagger UI for API documentation is available at:
   ```sh
   http://localhost:8080/swagger-ui/index.html
   ```
   This interface provides a visual overview of all the RESTful endpoints in your application.

5. **Stop the Application:**
   To stop the application and remove the containers, use:
   ```sh
   docker-compose down
   ```

## Further improvements

- [ ] **Logging**: Implement structured logging for better observability.
- [ ] **Metrics**: Implement metrics to monitor the application's performance.
- [ ] **Distributed Tracing**: Implement distributed tracing to correlate and group logs across the entire stack.
- [ ] **Performance**: Create a scheduled task to synchronize exchange rates periodically.