Certainly! Below is an outline of what your `README.md` file could include to provide a clear overview of your project:

---

# User Management API

This project implements a User Management API using Spring Boot, allowing CRUD operations on user entities.

## Requirements

- Java 17.0.10 Coretto
- Spring Boot 3.2.5
- PostgreSQL (database configuration provided)
- API responses in JSON format
- Unit tests using Spring `UserControllerTest`
- Error handling for REST using `GlobalExceptionHandler`

## Installation

1. Clone the repository:

   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory:

   ```bash
   cd user-management-api
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

## Usage

### 1. Create User

- **Endpoint:** `PUT /api/users`
- **Description:** Creates a new user.
- **Request Body:** JSON format containing UserDTO.
  ```json
  {
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "birthDate": "1990-01-01",
    "address": "123 Street, City",
    "phoneNumber": "1234567890"
  }
  ```
- Users must be at least 18 years old, configurable in `resources/application.yml`.

### 2. Update User

#### 2.1 Update Specific Fields

- **Endpoint:** `PATCH /api/users/{id}`
- **Description:** Updates specific fields of a user.
- **Request Body:** JSON format containing fields to be updated.
  ```json
  {
    "firstName": "Jane",
    "address": "456 Avenue, Town"
  }
  ```

#### 2.2 Update All Fields

- **Endpoint:** `PUT /api/users/{id}`
- **Description:** Updates all fields of a user.
- **Request Body:** JSON format containing complete user details.

### 3. Delete User

- **Endpoint:** `DELETE /api/users/{id}`
- **Description:** Deletes a user by ID.

### 4. Search Users by Birth Date Range

- **Endpoint:** `GET /api/users?fromDate={fromDate}&toDate={toDate}`
- **Description:** Retrieves a list of users within the specified birth date range.
- **Query Parameters:**
  - `fromDate`: Start date in YYYY-MM-DD format.
  - `toDate`: End date in YYYY-MM-DD format.
- **Response:** List of user objects in JSON format.

## Testing

Unit tests for the UserController are available in `UserControllerTest`. Execute the tests using your preferred test runner.

## Error Handling

The application includes a `GlobalExceptionHandler` to handle exceptions gracefully and provide appropriate error responses.

---

This README provides a comprehensive guide to understand and utilize the User Management API. Make sure to replace `<repository-url>` with the actual URL of your repository.
