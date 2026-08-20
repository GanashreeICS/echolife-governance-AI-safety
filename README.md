# EchoLife Backend 🌿

EchoLife is an AI-powered life logging, personal reflection, and digital memory archive application. The backend is built with Spring Boot 4, Java 26, Spring Data JPA, and PostgreSQL, featuring time-capsule locking mechanisms and automated reflection workflows.

---

## 📁 Project Directory Structure

```text
echolife-backend/
├── src/
│   ├── main/
│   │   ├── java/com/echolife/backend/
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java               # Cross-Origin Resource Sharing settings
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java           # Authentication & session endpoints
│   │   │   │   ├── MemoryController.java         # Memory CRUD & time capsule endpoints
│   │   │   │   ├── PromptController.java         # Reflection prompt endpoints
│   │   │   │   └── UserController.java           # User profile management
│   │   │   ├── dto/
│   │   │   │   ├── AuthResponse.java             # Auth payload responses
│   │   │   │   ├── LoginRequest.java             # User login payload
│   │   │   │   └── RegisterRequest.java          # User registration payload
│   │   │   ├── entity/
│   │   │   │   ├── Memory.java                   # Memory & time-capsule JPA model
│   │   │   │   ├── Prompt.java                   # AI reflection prompt JPA model
│   │   │   │   └── User.java                     # Application user JPA model
│   │   │   ├── repository/
│   │   │   │   ├── MemoryRepository.java         # Memory query methods & JPQL
│   │   │   │   ├── PromptRepository.java         # Prompt storage queries
│   │   │   │   └── UserRepository.java           # User lookup & authentication queries
│   │   │   ├── service/
│   │   │   │   ├── AiReflectionService.java      # Automated reflection generation logic
│   │   │   │   ├── AuthService.java              # User registration & credential hashing
│   │   │   │   ├── MemoryService.java            # Memory business logic & capsule filters
│   │   │   │   └── PromptService.java            # Prompt retrieval & assignments
│   │   │   └── EcholifeBackendApplication.java   # Spring Boot entry point
│   │   └── resources/
│   │       ├── application.properties            # Database and application configuration
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/echolife/backend/
│           ├── AuthControllerTest.java           # MockMvc web layer unit tests
│           ├── AuthServiceTest.java              # Authentication unit tests
│           ├── MemoryServiceTest.java            # Memory & time capsule unit tests
│           └── EcholifeBackendApplicationTests.java
├── docker-compose.yml                            # Multi-container orchestration (DB + App)
├── Dockerfile                                    # Multi-stage JDK 26 build specification
├── pom.xml                                       # Maven build & dependencies descriptor
└── README.md                                     # Project overview and API guide