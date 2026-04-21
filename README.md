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
    - Spring Security
- **Hibernate**
- **MySQL & H2**
- **Maven**
- **Docker**
- **Swagger & OpenAPI**
- **Postman**
- **JUnit & Mockito**
- **Lombok**
- **PlantUML**
- **GitHub Actions**

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

The project began with a simple goal:
> “I've been cooking a lot recently, how can I manage my recipes?”

Very quickly, it turned into something more serious once I started asking:

- How should the API be structured?
- How do I manage transactions?
- How much logic belongs in controllers vs services?

### What I wanted to achieve

- A **readable and maintainable Spring Boot project**
- Clear separation of responsibilities
- A project that resembles something you’d see in a real codebase, not just a tutorial

### How it turned out?

The result is a backend-focused application with:

- a well-defined domain model for recipes and customers
- predictable REST API behavior with clear resource structure
- customer registration and customer profile management features
- support for assigning, viewing, and managing customer-owned recipes
- relational data handling between customers and recipes
- input validation and structured error responses
- room for extension (advanced security, ratings, rate-limiting, payments, etc.)

---

## 🚀 Running the project

### Requirements

- Java 21+
- Docker (Optional)

### Run locally

1. Download the latest application JAR [there](https://github.com/Askie01/recipe-application/packages/2831879?version=3.0.0) 
   and select `recipe-application-3.0.0.jar`
2. Run the application locally with no extra setup!

```bash
java -jar recipe-application-4.0.0.jar
```

3. Application will start on: `http://localhost:8080`

### Run with Docker

```bash
docker-compose up
```

---

## 📘 API Documentation

The API is documented using **Swagger / OpenAPI**.

Once the application is running, you can explore and test all endpoints here:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

👉 Example:  
To review the recipe endpoints, navigate to: ``Customers API v1`` or ``Recipes API v3``

📂 **Additional documentation**

- API structure and flow diagrams (PlantUML)
- High-level architecture overview  
  All of the above can be found in the [`/docs`](./docs) directory.

## 📦 **Postman collection**

A ready-to-use Postman collection with example requests is available in
the [Postman collection](https://www.postman.com/askie01/recipe-application/overview)
It contains preconfigured endpoints, sample payloads, and environment variables for quick testing.

---

## 📌 Final Notes / Project Status

This project is being marked as completed and further development will be paused.

The Recipe Application was intentionally designed as a simple, monolithic Spring Boot project 
to focus on learning and implementing core backend concepts such as REST API design, 
database relationships, and basic user management.

As the main learning objectives have been achieved, continued development is no longer planned. 
Instead, effort will shift toward a new, more advanced microservice-based project, 
where the focus will be on scalability, distributed systems, service communication, 
and cloud-ready architecture.

---

## 📚 What was learned during this project

Throughout the development of this application, several important backend concepts were reinforced:

Designing and building RESTful APIs using Spring Boot
Working with relational databases and JPA entity relationships
Handling one-to-many and many-to-many relationships in a real-world context
Structuring layered architecture (controller, service, repository)
Implementing validation and improving data integrity
Writing maintainable and modular backend code
Improving understanding of API error handling and response design

---

## 🚀 Next Steps

The next phase of development will focus on:

Microservice architecture
Service-to-service communication
Distributed system design
Scalability and deployment strategies (Kubernetes, cloud environments)

This project served as a solid foundation for transitioning into more advanced backend system design.