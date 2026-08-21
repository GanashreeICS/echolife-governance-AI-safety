# EchoLife - AI Governance & Safety Engine

A robust, enterprise-ready Spring Boot microservice designed for **Consent Governance**, **AI Persona Orchestration**, and **Real-Time Safety & Risk Screening**.

---

## Key Features

* **Consent Governance Framework**: Strict enforcement of granular user consent (`AI_DATA_PROCESSING`, `LEGACY_SHARING`, `TIME_CAPSULE_ACCESS`) prior to any data or AI processing.
* **Rule-Based AI Safety Engine**: Multi-tiered safety screening that scans for potential self-harm, harassment, hate speech, and violent content, blocking high-risk inputs before reaching AI models.
* **Dynamic Persona Management**: Configurable system personas (`The Compassionate Mentor`, `The Poetic Chronicler`) supporting contextual response modes (`REFLECTION`, `ADVICE`, `STORY`, `BLESSING`).
* **Memory & Reflection Pipeline**: Full memory lifecycle management integrated with automated AI-assisted reflection and summarization.
* **Security & Authorization**: Stateless JWT authentication with role-based access control (RBAC) via Spring Security.
* **API Documentation**: Interactive OpenAPI 3 / Swagger UI interface with integrated Bearer token authorization.

---

## Tech Stack

* **Language / Framework**: Java 17, Spring Boot 3.3.2
* **Security**: Spring Security 6, JJWT (io.jsonwebtoken)
* **Persistence**: Spring Data JPA, Hibernate, PostgreSQL
* **Validation**: Jakarta Validation API (Hibernate Validator)
* **API Docs**: SpringDoc OpenAPI UI 2.5.0
* **Build Tool**: Maven

---

## Getting Started

### Prerequisites

* **Java Development Kit (JDK)**: Version 17 or higher
* **PostgreSQL Database**: Running on `localhost:5432` with a database named `echolife`
* **Maven**: Bundled with IntelliJ IDEA or installed locally

### Database Configuration

Update `src/main/resources/application.properties` with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/echolife
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true