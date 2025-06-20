# 🐾 Animal Adoption Application

An application that allows users to:
- Browse animals available for adoption
- Send adoption requests
- Make donations
- Volunteer for animal-related activities
- Access educational content about animal care

## 🛠️ Backend Architecture (Microservices)

This project is built using a **microservices architecture**, and includes the following services:

### Core Microservices
- **User Microservice** – manages user data and roles
- **Pet Microservice** – handles animals and adoption listings
- **Donation Microservice** – manages donation functionality and volunteering
- **Education Microservice** – provides educational articles and content about animals

### Utility Microservices
- **Config Server** – centralized configuration management for all services
- **Eureka Server** – service discovery and load balancing
- **System Events** – logs and tracks all internal system activities
- **API Gateway** – unified entry point for all backend services

## 🧰 Technological Stack

- **Backend:** Spring Boot
- **Frontend:** React
- **Database:** MySQL
- **Messaging:** RabbitMQ


## 🎥 Demo Video

You can watch the demo video of the application [here](https://drive.google.com/file/d/1o21Q4if__8CNKjCOPgBKK7Qm-_4fwzho/view?usp=sharing).


## 👥 Contributors

- [Balta Džana](https://github.com/dbalta1)
- [Klanco Emira](https://github.com/eklanco1)
- [Zukić Ajla](https://github.com/azukic1)

---

# 🐾 Animal Adoption System – Docker Deployment

This is a microservices-based application for managing the animal adoption process, developed using Spring Boot and React. The system uses Docker containers for simple and isolated deployment of all components.

---

## 📦 System Overview

- **Frontend**: React application
- **Backend Microservices**:
  - `AnimalAdoptionUser` – user registration
  - `AnimalAdoptionPet` – pet management
  - `AnimalAdoptionNotification` – e-mail notifications (RabbitMQ)
  - `AnimalAdoptionDonation` – donations management
  - `AnimalAdoptionEducation` – educational articles
- **Infrastructure**:
  - `Config Server` – centralized configuration
  - `Eureka Server` – service registry
  - `API Gateway` – single entry point
  - `RabbitMQ` – asynchronous communication
  - `MySQL` databases – one for each microservice

---

## ⚙️ Prerequisites

- [Docker](https://www.docker.com/) installed
- [Docker Compose](https://docs.docker.com/compose/) installed
- Make sure the following ports are available: 3000, 8080, 8761, 8888, 15672, 5672...

---

## 🔧 Building All Microservices

First, build the `.jar` files for all Spring Boot services:

```bash
cd AnimalAdoptionUser
mvn clean package -DskipTests

cd ../AnimalAdoptionPet
mvn clean package -DskipTests

cd ../AnimalAdoptionNotification
mvn clean package -DskipTests

cd ../AnimalAdoptionDonation
mvn clean package -DskipTests

cd ../AnimalAdoptionEducation
mvn clean package -DskipTests

cd ../ConfigServer
mvn clean package -DskipTests

cd ../EurekaServer
mvn clean package -DskipTests

cd ../apiGateway
mvn clean package -DskipTests

🐳 Docker Build

Build all services:

docker compose build

Or, to build and run all services at once:

docker compose up -d --build

▶️ Running the Application

Start all services:

docker compose up -d

❌ Stopping the Application

docker compose down

🌐 Access URLs
Service	URL
Frontend (React)	http://localhost:3000
API Gateway	http://localhost:8080
Eureka Dashboard	http://localhost:8761
Config Server	http://localhost:8888
RabbitMQ UI	http://localhost:15672 (guest/guest)

🧠 Notes

    In application.properties files, use Docker service names as hosts (e.g. mysql-user, eureka-server, config-server, rabbitmq) instead of localhost.

    Use mem_limit and JAVA_TOOL_OPTIONS in Dockerfiles to optimize memory usage.

    The Notification microservice listens for asynchronous events via RabbitMQ (user.created queue).

    Make sure all .jar files are generated in the target/ directories before starting the application.


📁 Project Structure (Simplified)

/
├── docker-compose.yml
├── frontend/
│   ├── Dockerfile
│   └── nginx.conf
├── AnimalAdoptionUser/
│   └── Dockerfile
├── AnimalAdoptionNotification/
│   └── Dockerfile
├── ...


> This application was developed as part of a student project aiming to promote animal welfare through a user-friendly and modular platform.


