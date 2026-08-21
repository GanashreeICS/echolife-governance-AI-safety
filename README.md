# EchoLife - AI Governance & Safety Backend

A comprehensive, production-grade Spring Boot backend service that combines personal memory journaling, locked time capsules, and prompt-driven reflection with an enterprise-grade **Consent Governance**, **AI Persona Orchestration**, and **Rule-Based Safety Screening Engine**.

---

## Table of Contents

- [Project Overview](#project-overview)
- [End-to-End Architecture Flow](#end-to-end-architecture-flow)
- [Core Features & Modules](#core-features--modules)
    - [1. User Management & JWT Authentication](#1-user-management--jwt-authentication)
    - [2. Reflective Prompts Engine](#2-reflective-prompts-engine)
    - [3. Memory & Time Capsule Management](#3-memory--time-capsule-management)
    - [4. AI Reflection & Summarization](#4-ai-reflection--summarization)
    - [5. Consent & Privacy Governance](#5-consent--privacy-governance)
    - [6. AI Content Safety & Risk Screening](#6-ai-content-safety--risk-screening)
    - [7. Dynamic Persona & Response Modes](#7-dynamic-persona--response-modes)
- [Tech Stack](#tech-stack)
- [Database Schema Overview](#database-schema-overview)
- [Getting Started & Local Setup](#getting-started--local-setup)
- [API Endpoints Reference](#api-endpoints-reference)
- [Testing via Swagger UI](#testing-via-swagger-ui)
- [Error Handling & Governance Status Codes](#error-handling--governance-status-codes)
- [License](#license)

---

## Project Overview

**EchoLife** is designed as a secure digital legacy and reflective journaling ecosystem. It empowers users to record life experiences, lock future time capsules, and receive meaningful AI reflections—all while enforcing strict privacy consent guardrails and proactive content safety filters before any text reaches downstream AI processing.

---

## End-to-End Architecture Flow[ Client / Frontend / Swagger UI ]
│
▼
[ Spring Security + JWT Filter ]
│ (Valid Bearer Token)
▼
[ Controller Layer ]
│
▼
[ Governance & Safety Guard ]
├── 1. Verify Active Consent (e.g., AI_DATA_PROCESSING) ──► (Fail: 403 Forbidden)
└── 2. Scan Text for Risk/Harm (Self-Harm, Toxicity) ────► (Fail: 400 Bad Request)
│ (All Checks Passed)
▼
[ AI Reflection Engine ]
├── Load Persona (Mentor / Chronicler)
├── Apply Response Mode (REFLECTION, ADVICE, STORY, BLESSING)
└── Generate Analysis & Summary
│
▼
[ Persistence Layer (Spring Data JPA) ]
│
▼
[ PostgreSQL Database ]


---

## Core Features & Modules

### 1. User Management & JWT Authentication
- User registration and login with encrypted password storage via BCrypt.
- Stateless session management using JSON Web Tokens (JJWT).
- Secure authorization headers across protected memory and governance endpoints.

### 2. Reflective Prompts Engine
- Curated repository of introspective daily prompts and categories.
- Capability to create memories directly anchored to specific prompt IDs.

### 3. Memory & Time Capsule Management
- Standard memory logging with emotional tone tagging, creation dates, and metadata.
- **Time Capsule Engine:** Allows locking memories until a specific future date (`unlockDate`). Locked capsules remain inaccessible until the unlock threshold is reached.

### 4. AI Reflection & Summarization
- Automated generation of reflective insights (`aiReflection`) and concise summaries (`aiReflectionSummary`) tailored to user memory narratives.

### 5. Consent & Privacy Governance
- Granular consent tracking per user (`AI_DATA_PROCESSING`, `TIME_CAPSULE_ACCESS`, `LEGACY_SHARING`).
- Complete audit trail of consent timestamps (`grantedAt`, `revokedAt`).
- Strict runtime interception: AI processing is rejected if consent is missing or revoked.

### 6. AI Content Safety & Risk Screening
- Multi-tier safety guardrails scanning user submissions for sensitive content, self-harm signals, hate speech, and violent text.
- Rejects hazardous inputs with descriptive validation feedback prior to executing AI tasks.

### 7. Dynamic Persona & Response Modes
- Selectable AI personalities (e.g., *The Compassionate Mentor*, *The Poetic Chronicler*).
- Contextual response modes:
    - `REFLECTION`: Empathetic analysis and emotional resonance.
    - `ADVICE`: Grounded, practical guidance.
    - `STORY`: Narrative prose expanding on the memory.
    - `BLESSING`: Poetic, uplifting closing remarks.

---

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.3.2
- **Security:** Spring Security 6, JJWT (io.jsonwebtoken)
- **Data Persistence:** Spring Data JPA, Hibernate ORM
- **Database:** PostgreSQL (Driver 42.7.x)
- **Validation:** Jakarta Bean Validation (Hibernate Validator)
- **API Documentation:** SpringDoc OpenAPI 3 / Swagger UI 2.5.0
- **Build Tool:** Apache Maven

---

## Database Schema Overview

The PostgreSQL database (`echolife`) consists of the following core entities:

- **`users`**: User credentials, roles, and profiles.
- **`prompts`**: Pre-seeded reflective prompts and categories.
- **`memories`**: Stored memories, emotional tone, time capsule locks, and generated AI reflections.
- **`user_consents`**: Granular consent permissions and status history.
- **`personas`**: System and user AI personas with allowable response modes.

---

## Getting Started & Local Setup

### Prerequisites
- **JDK 17** or higher
- **PostgreSQL 14+** running locally
- **Maven** (or use the IntelliJ embedded Maven runner)

### 1. Database Setup
Create a PostgreSQL database named `echolife`:
```sql
CREATE DATABASE echolife;