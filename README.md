# CMS Backend

This repository contains a Spring Boot based backend for a simple content management system. It exposes REST APIs for managing media content and associated licenses.

## Prerequisites

- JDK 11
- Maven or the provided `mvnw` wrapper
- A running PostgreSQL instance configured with the connection details from `src/main/resources/application.properties` (by default `jdbc:postgresql://localhost:5432/db_content` with user `admin` and password `1234`).

## Build

```bash
./mvnw clean package
```

## Run

```bash
./mvnw spring-boot:run
```

## Tests

```bash
./mvnw test
```

Running the above command executes the unit tests located under
`src/test/java`. Maven will automatically download required dependencies on
the first run. If all tests pass, the build is considered healthy.

## REST Endpoints

### Content
- `GET /content/all` – list all content
- `GET /content/find/id/{id}` – get a content item by ID
- `GET /content/find/name/{name}` – get a content item by name
- `POST /content/add` – create new content
- `PUT /content/update/{contentId}` – update existing content
- `PUT /content/{contentId}/license/{licenseId}` – assign a license to content
- `DELETE /content/delete/{contentId}` – remove a content item
- `DELETE /content/delete/{contentId}/license/{licenseId}` – remove a license from content

### License
- `GET /license/all` – list all licenses
- `GET /license/find/{id}` – get a license by ID
- `POST /license/add` – create a new license
- `PUT /license/update/{licenseId}` – update a license
- `DELETE /license/delete/{licenseId}` – delete a license

