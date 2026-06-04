# 🌍 AI-Driven Travel Microservices Platform (MCP + Spring Cloud)

This repository contains a microservices-based travel application integrated with a **Model Context Protocol (MCP)** server. The platform leverages an LLM (via Ollama) to dynamically look up weather data, flight schedules, and hotel options by translating natural language prompts into reactive microservice REST API calls.

---

## 🏗️ System Architecture & Startup Order

To avoid routing issues, the applications **must** be started in a specific sequence. Infrastructure services (Registry and Gateway) need to be running and healthy before the business applications and AI components come online.



1. **Discovery Server (Eureka)**: Coordinates service registration and discovery.
2. **API Gateway**: Provides a single reverse-proxy entry point (`port 8080`) with built-in routing rules and circuit breakers.
3. **Core Microservices**: Independent functional units (`weather`, `flights`, `hotels`) backing the core platform.
4. **MCP Client/Server**: Connects the Ollama LLM to your microservices network via Spring AI reactive tools.

---

## 🛠️ Prerequisites

Ensure you have the following software installed locally:
* **Java 21** or higher
* **Maven 3.9+**
* **Ollama** (with your preferred model pulled locally, e.g., `ollama pull llama3` or `mistral`)
* **MySQL Database** (Ensure your local instances match the profiles configured in the microservices)

---

## 🚀 Step-by-Step Execution Guide

Open separate terminal windows for each component and run the following commands from the root directory of the project:

### Step 1: Start the Discovery Server (Eureka)
The backbone of your service infrastructure. Wait for the dashboard to become accessible.
```bash
cd discovery-server
mvn spring-boot:run
