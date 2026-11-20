# Employee Management - Module 7

Spring Boot employee REST API demonstrating SLF4J/Logback application logging and profile-specific database configuration.

## Logging

`EmployeeService` logs successful employee creation, update, and deletion at `INFO`. Name searches are logged at `DEBUG`. Spring Boot uses SLF4J with Logback by default; no extra logging dependency is needed.

## Profiles

The default profile is `dev` (`application.yml`):

- `application-dev.yml` uses an in-memory H2 database and enables SQL/debug logging.
- `application-prod.yml` uses PostgreSQL. Supply database credentials through environment variables; never commit production secrets.

Run in development:

```bash
mvn spring-boot:run
```

Run with production profile:

```bash
DB_URL=jdbc:postgresql://db-host:5432/employee_management \
DB_USERNAME=employee_app DB_PASSWORD='your-secret' \
java -jar target/employee-management-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## API

- `GET /api/employees` — list employees; optional `?name=...` search
- `GET /api/employees/{id}` — get employee
- `POST /api/employees` — create
- `PUT /api/employees/{id}` — update
- `DELETE /api/employees/{id}` — delete

Example body:

```json
{"name":"Nguyen Van A","email":"a@example.com"}
```
