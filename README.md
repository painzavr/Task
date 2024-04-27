1. It has the following fields:
1.1. Email (required). Add validation against email pattern
1.2. First name (required)
1.3. Last name (required)
1.4. Birth date (required). Value must be earlier than current date
1.5. Address (optional)
1.6. Phone number (optional)
2. It has the following functionality:
2.1. Create user. PutMapping `api/users` provided with JSON of UserDTO
It allows to register users who are more than [18] years old. The value [18] should be taken from properties file. -> `resources/application.yml`
2.2. Update one/some user fields -> PatchMapping `api/users`
2.3. Update all user fields -> PutMapping `api/users`
2.4. Delete user -> DeleteMapping `api/users?id={id}`
2.5. Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects -> GetMapping `api/users` provided with JSON of DateRange (fromDate, toDate)
3. Code is covered by unit tests using Spring `UserControllerTest`
4. Code has error handling for REST `GlobalExceptionHandler`
5. API responses are in JSON format
6. Use of database is not necessary. The data persistence layer is not required. -> PosgreSQL:
    url: jdbc:postgresql://localhost:5432/users
         username: postgres
         password: 123
7. Any version of Spring Boot -> 3.2.5. Java version of your choice -> 17.0.10 Coretto
8. You can use Spring Initializer utility to create the project: Spring Initializr
