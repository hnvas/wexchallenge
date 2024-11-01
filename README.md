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
├── wexchallenge
    ├── Application # Use cases and application services
    ├── Domain # Domain entities
    └── Infrastructure # Infrastructure components
      ├── Api # API documentation, controllers, and exception handlers
      ├── Client # External API clients
      ├── Config # Application configuration
      ├── Gateway # Multisource data management
      ├── Persistence # Database access layer
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

### Steps:

To run the tests, use the following command:
   ```sh
   ./gradlew test
   ```

## Running the Application

### Prerequisites:

- Docker and Docker Compose installed on your machine.

### Steps:

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/hnvas/wexchallenge.git
   cd wexchallenge
   ```
2. **Build and Run with Docker Compose:**
   This will build the Java application Docker image and start the PostgreSQL database along with the application.
   ```sh 
    ./gradlew assemble && docker-compose up -d
   ```
3. **Accessing the Application:**
   The application will be accessible at `http://localhost:8080`.

4. **Accessing API Documentation:
   The Swagger UI for the API documentation can be accessed at http://localhost:8080/swagger-ui/index.html. This provides a visual interface for all the RESTful endpoints in your application.

5. **Stopping the Application:**
   To stop the application and remove the containers, use:
   ```sh 
    docker-compose down
   ```