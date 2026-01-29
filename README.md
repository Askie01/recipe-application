# 🍳 Recipe Management API

A Spring Boot–based REST API for managing cooking recipes — built to practice clean backend architecture, REST design,
and real-world development trade-offs.

This project focuses on **how a backend service for a recipe application could look in production**, not just on “making
it work”.

---

## 📌 What is the project about?

The Recipe Management API allows users to create, browse, update, and manage cooking recipes.  
Each recipe contains structured data such as ingredients, measure units, and categories.

The main goal of this project was to:

- design a **clean and scalable REST API**
- apply **Spring Boot best practices**
- model a realistic domain instead of a toy example

---

## 🛠 Technologies

- **Java 21**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- **Hibernate / JPA**
- **MySQL / H2**
- **Maven**
- **Docker**
- **Swagger / OpenAPI**
- **PlantUML**

---

## ✨ Features

- CRUD operations for recipes
- Recipe ingredients and preparation steps
- Request validation and error handling
- Clean layered architecture (controller → service → repository)
- REST-friendly HTTP status codes
- OpenAPI / Swagger UI documentation
- Environment-based configuration (local / docker)

---

## 🧠 Process

### How it started

The project started as a simple idea:
> “Let me build a small CRUD app for recipes.”

Very quickly, it turned into something more serious once I started asking:

- How should the API be structured?
- How do I manage transactions?
- How much logic belongs in controllers vs services?

### What I wanted to achieve

- A **readable and maintainable Spring Boot project**
- Clear separation of responsibilities
- A project that resembles something you’d see in a real codebase, not just a tutorial

### How it turned out

The result is a backend-focused application with:

- a well-defined domain
- predictable API behavior
- room for extension (auth, ratings, users, etc.)

---

## 🚀 Running the project

### Requirements

- Java 21+
- Maven
- MySQL (can run with Docker)
- Docker

### Run locally

```bash
mvn clean spring-boot:run
```

### Run with Docker

```bash
docker-compose up
```

## 📘 API Documentation

The API is documented using **Swagger / OpenAPI**.

Once the application is running, you can explore and test all endpoints here:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

👉 Example:  
To review the recipe endpoints, navigate to: ``Recipes API v1``

📂 **Additional documentation**

- API structure and flow diagrams (PlantUML)
- High-level architecture overview  
  All of the above can be found in the [`/docs`](./docs) directory.

## 📦 **Postman collection**

A ready-to-use Postman collection with example requests is available in
the [Postman collection](https://www.postman.com/askie01/recipe-application/overview)
It contains preconfigured endpoints, sample payloads, and environment variables for quick testing.

## 🚧 Coming Soon

Planned features and improvements:

- Refinement and simplification of API contracts
- User registration and authentication (Basic Auth-based)
- Query optimization and database indexing
- Advanced filtering and pagination support
- Improved test coverage with integration tests
- Basic user-facing UI for API interaction