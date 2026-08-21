# EchoLife - AI Governance & Safety Backend 🌿

EchoLife is an AI-powered life logging, personal reflection, and digital memory archive application. The backend is built with Spring Boot 3.3.2, Java 17, Spring Data JPA, and PostgreSQL, featuring time-capsule locking mechanisms, dynamic AI persona orchestration, strict consent governance, and automated safety screening workflows.

---

## 📁 Project Directory Structure

```text
echolife-backend/
├── src/
│   ├── main/
│   │   ├── java/com/echolife/backend/
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java                # Cross-Origin Resource Sharing settings
│   │   │   │   ├── OpenApiConfig.java             # Swagger / OpenAPI 3 Bearer Auth configuration
│   │   │   │   └── SecurityConfig.java            # Spring Security 6 & JWT filter chain
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java            # Registration & JWT login endpoints
│   │   │   │   ├── ConsentController.java         # User consent governance & audit endpoints
│   │   │   │   ├── MemoryController.java          # Memory CRUD & time capsule endpoints
│   │   │   │   ├── PersonaController.java         # AI persona management endpoints
│   │   │   │   ├── PromptController.java          # Reflection prompt catalog endpoints
│   │   │   │   └── UserController.java            # User profile management
│   │   │   ├── dto/
│   │   │   │   ├── AuthResponse.java              # JWT authentication response payload
│   │   │   │   ├── ConsentRequest.java            # User consent grant/revoke payload
│   │   │   │   ├── LoginRequest.java              # User login credentials
│   │   │   │   ├── MemoryRequest.java             # Memory submission with tone & persona settings
│   │   │   │   ├── PersonaRequest.java            # Custom persona creation payload
│   │   │   │   └── RegisterRequest.java           # User registration payload
│   │   │   ├── entity/
│   │   │   │   ├── Memory.java                    # Memory & time-capsule JPA entity (TEXT fields)
│   │   │   │   ├── Persona.java                   # AI persona entity with response modes
│   │   │   │   ├── Prompt.java                    # Reflection prompt JPA entity
│   │   │   │   ├── User.java                      # Application user & credentials model
│   │   │   │   └── UserConsent.java               # Granular user consent tracking entity
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java    # Centralized REST error interceptor
│   │   │   │   ├── GovernanceException.java       # Missing consent / safety violation exception
│   │   │   │   └── ResourceNotFoundException.java # Entity lookup exception
│   │   │   ├── repository/
│   │   │   │   ├── MemoryRepository.java          # Memory query methods & time capsule filters
│   │   │   │   ├── PersonaRepository.java         # AI persona query methods
│   │   │   │   ├── PromptRepository.java          # Reflection prompt queries
│   │   │   │   ├── UserConsentRepository.java     # Active consent validation queries
│   │   │   │   └── UserRepository.java            # User lookup & authentication queries
│   │   │   ├── security/
│   │   │   │   ├── JwtAuthenticationFilter.java   # Bearer token validation filter
│   │   │   │   └── JwtTokenProvider.java          # JWT generation & parsing utility
│   │   │   ├── service/
│   │   │   │   ├── AiReflectionService.java       # AI persona reflection & summarization pipeline
│   │   │   │   ├── AiSafetyService.java           # Content risk & multi-rule text screening
│   │   │   │   ├── AuthService.java               # User registration, authentication & password hashing
│   │   │   │   ├── ConsentService.java            # Consent enforcement & audit lifecycle
│   │   │   │   ├── MemoryService.java             # Memory processing, governance checks & capsule filtering
│   │   │   │   ├── PersonaService.java            # System & custom AI persona orchestrator
│   │   │   │   └── PromptService.java             # Prompt catalog management
│   │   │   └── EcholifeBackendApplication.java    # Spring Boot entry point & data seeder
│   │   └── resources/
│   │       ├── application.properties             # PostgreSQL and JPA application configurations
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/echolife/backend/
│           ├── AuthControllerTest.java            # Web layer authentication tests
│           ├── AuthServiceTest.java               # Credentials & JWT unit tests
│           ├── ConsentServiceTest.java            # Governance enforcement unit tests
│           ├── MemoryServiceTest.java             # Memory & time capsule unit tests
│           └── EcholifeBackendApplicationTests.java
├── pom.xml                                        # Maven build & dependencies descriptor
└── README.md      Complete Feature Breakdown User Identity & JWT Security: BCrypt password hashing, stateless session verification with JWT Bearer tokens, and role-based endpoint protection.Prompts Catalog: Categorized repository of daily reflection prompts for structured journaling.Memory & Time Capsule Engine: Narrative journaling with emotional tone tracking, future unlock timestamps (unlockDate), and unlocked state queries.Consent Governance Framework: Explicit runtime verification of user permissions (AI_DATA_PROCESSING, TIME_CAPSULE_ACCESS, LEGACY_SHARING) before executing AI workflows.Rule-Based AI Safety Engine: Multi-tiered screening that catches self-harm, hate speech, harassment, and violent content before processing.Dynamic Persona Orchestration: Adaptable AI reflections using configurable personalities (The Compassionate Mentor, The Poetic Chronicler) with selectable response modes (REFLECTION, ADVICE, STORY, BLESSING).Repository Identity & Maven Configuration: Migrated project identity to echolife-governance-AI-safety and updated pom.xml build settings for Spring Boot 3.3.2 and Java 17.Database & Schema Synchronization: Resolved entity DDL constraints and upgraded PostgreSQL column data types (ai_reflection, ai_reflection_summary, description, system_prompt, question) to TEXT for unlimited character capacity.Interactive OpenAPI 3 / Swagger Documentation: Embedded Bearer authentication authorization directly into /swagger-ui/index.html for API verification.🛠️ Tech StackLanguage & Framework: Java 17, Spring Boot 3.3.2Security: Spring Security 6, JJWT (io.jsonwebtoken)Persistence: Spring Data JPA, Hibernate 6.5.2 ORM, PostgreSQL DriverConnection Pooling: HikariCPValidation: Jakarta Bean Validation (Hibernate Validator)API Documentation: SpringDoc OpenAPI UI 2.5.0 (Swagger 3)Build Tool: Apache Maven⚙️ Database ConfigurationUpdate src/main/resources/application.properties with your PostgreSQL database credentials:Propertiesspring.application.name=echolife-governance-AI-safety

# PostgreSQL Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/echolife
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate / JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# OpenAPI / Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
🚦 Getting Started1. Build and RunBashmvn clean install
mvn spring-boot:run
Or execute EcholifeBackendApplication.java directly in IntelliJ IDEA. The service will be available on http://localhost:8080.2. Interactive Swagger DocumentationAccess the API UI in your browser:Plaintexthttp://localhost:8080/swagger-ui/index.html
📡 API Endpoints Reference🔐 Authentication (/api/auth)MethodEndpointDescriptionPOST/api/auth/registerRegister a new userPOST/api/auth/loginLog in and receive a JWT Bearer token🛡️ Consent Governance (/api/consents)MethodEndpointDescriptionPOST/api/consents/user/{userId}/grantGrant specific consent (AI_DATA_PROCESSING)POST/api/consents/user/{userId}/revokeRevoke user consentGET/api/consents/user/{userId}Retrieve all active consents for a user🎭 AI Personas (/api/personas)MethodEndpointDescriptionGET/api/personas/user/{userId}List all available system & custom personasPOST/api/personas/user/{userId}Create a custom AI persona💡 Daily Prompts (/api/prompts)MethodEndpointDescriptionGET/api/promptsRetrieve all reflection promptsGET/api/prompts/category/{category}Filter prompts by categoryPOST/api/promptsCreate a new prompt (Admin)📝 Memories & Time Capsules (/api/memories)MethodEndpointDescriptionPOST/api/memories/user/{userId}Create memory (runs consent check + safety screen + AI)POST/api/memories/user/{userId}/prompt/{promptId}Create memory linked to a promptGET/api/memories/user/{userId}Get all memories for a userGET/api/memories/user/{userId}/accessibleGet unlocked memories onlyGET/api/memories/user/{userId}/time-capsules/lockedList locked time capsulesDELETE/api/memories/{id}Delete a memory by ID🧪 Testing Workflow (Swagger UI)Login: Call POST /api/auth/login to obtain your JWT token.Authorize: Click Authorize 🔓 in Swagger UI and submit your Bearer token.Grant Consent: Send POST /api/consents/user/{userId}/grant with "consentType": "AI_DATA_PROCESSING".Create Safe Memory: Submit memory content to POST /api/memories/user/{userId} with responseMode: "REFLECTION" to generate an AI reflection.Verify Safety Screen: Post risky/flagged content to verify the safety engine returns a 400 Bad Request violation error.🛑 Error Handling & Governance Status CodesStatus CodeDescriptionTrigger400 Bad RequestValidation / Safety ViolationInput violates AI safety screening guardrails (self-harm, hate speech, violence)403 ForbiddenGovernance ViolationUser has not granted active consent for AI_DATA_PROCESSING404 Not FoundResource MissingSpecified user, memory, persona, or prompt ID does not exist500 Internal ErrorServer / Database ExceptionUnexpected database or runtime processing failure                                 # Comprehensive project documentation