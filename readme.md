# Student Management System

## Overview

The Student Management System is a Spring Boot application that provides CRUD operations for managing student records.
It supports fetching student details, creating, updating, and deleting student records, and filtering and pagination of
student lists.

## Features

- **Retrieve All Students**: Fetch all students with optional filtering.
- **Paginate Students**: Retrieve students with pagination support and optional filtering.
- **Get Student by ID**: Retrieve a student record by its unique ID.
- **Create Student**: Add a new student record.
- **Update Student**: Modify an existing student record by its ID.
- **Delete Student**: Remove a student record by its ID.

## Endpoints

### Get All Students

- **URL**: `/students/all`
- **Method**: `POST`
- **Description**: Fetches a list of students with optional filters.
- **Request Body**: List of `FilterCriteria` (optional).
- **Response**: `List<StudentDTO>`

### Get All Students with Pagination

- **URL**: `/students/page`
- **Method**: `POST`
- **Description**: Fetches a paginated list of students with optional filters.
- **Request Parameters**: `Pageable` for pagination.
- **Request Body**: List of `FilterCriteria` (optional).
- **Response**: `Page<StudentDTO>`

### Get Student by ID

- **URL**: `/students/{id}`
- **Method**: `GET`
- **Description**: Retrieves a student record by its ID.
- **Path Variable**: `id` - Student's unique identifier.
- **Response**: `StudentDTO`

### Create Student

- **URL**: `/students`
- **Method**: `POST`
- **Description**: Creates a new student record.
- **Request Body**: `StudentDTO` - Details of the student to be created.
- **Response**: `StudentDTO` - Created student record.

### Update Student

- **URL**: `/students/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing student record by its ID.
- **Path Variable**: `id` - Student's unique identifier.
- **Request Body**: `StudentDTO` - Updated details of the student.
- **Response**: `StudentDTO` - Updated student record.

### Delete Student

- **URL**: `/students/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a student record by its ID.
- **Path Variable**: `id` - Student's unique identifier.
- **Response**: Success message.

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- PostgreSQL Driver
- Spring Boot Starter Validation
- Flyway Core and Database PostgreSQL
- Lombok
- JUnit 5 and Mockito
- Log4j2 for logging

## Build and Run

To build and run the application:

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   ```

2. **Navigate to the project directory**:

   ```bash
   cd studentmanagement
   ```
3. **Create empty postgres DB and configure applications.properties from sample.applications.properties**:
   tbl_student will be created via flyway.


4. **Build the project**:

   ```bash
   mvn clean install
   ```

5. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```



## Video Overview

For a detailed overview of the application, please watch the following video: [Loom Video](https://www.loom.com/share/b4dd751a8c42410090674622348c63dc?sid=f5ae5554-5dec-48c5-af44-ac05889831aa)
