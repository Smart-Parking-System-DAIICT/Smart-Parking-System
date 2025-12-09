# 🚗 **Smart Parking System – Digital Parking Management Web App**

Smart Parking System is a project developed by **Group TechSquad**, 3rd Semester, M.Sc. IT at DAIICT.
It’s designed to provide a digital solution for managing parking lots. Users can register, book slots, make payments, and generate receipts seamlessly.

## 🌐 **Live Demo**

https://smartparkingclean-production-53e0.up.railway.app/Home

## 1. **Tech Stack**
### 🖥️ **Frontend**
```
HTML5
CSS3
JavaScript
Bootstrap (for responsive UI)
```
### 🔥 **Backend / Services**
```
Java 17
Spring Boot
Spring MVC
Spring Data JPA
MySQL Database
```
### 🎯 **Features**
```
User Registration
Real-time Slot Availability
Slot Reservation (Date + Start Time + Duration)
Payments Module
Receipt Generation
Feedback Submission System
Responsive UI for all screens
```

### 🛠 **Development Tools**
```
Mockito – Service-level mocking
Git + GitHub – Version control
IntelliJ IDEA / VS Code
Maven – Build tool
```

### ⚙ **Build & Deployment Tools**
```
Docker – Containerization
Railway.app – Production deployment
Maven Wrapper (mvnw) – Build configuration
```

## 2. **Project Setup & Running Instructions**
### Clone the Repository
```
git clone 'https://github.com/Smart-Parking-System-DAIICT/Smart-Parking-System' <br>
cd Smart_Parking_Clean
```
### Install Dependencies <br>
```
./mvnw clean install
```
### ▶ Run the Application <br>
```
./mvnw spring-boot:run
```
### The app will start at: <br>
```
http://localhost:8080/
```

### 🐳 **Docker Setup (Used for Railway Deployment)** <br>
Dockerfile used:
```
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw -B package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```
## 3. **Project Structure**
```
Smart_Parking_Clean/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/Smart_Parking/
│   │   │       ├── Controller/
│   │   │       ├── DTO/
│   │   │       ├── Model/
│   │   │       ├── Repository/
│   │   │       ├── Service/
│   │   │       ├── Config/
│   │   ├── resources/
│   │   │   ├── templates/          (HTML files)
│   │   │   ├── static/             (CSS, JS, images)
│   │   │   ├── application.properties
│
│── test/
│   ├── SlotServiceTest.java
│   ├── UserServiceTest.java
│   ├── ReserveServiceTest.java
│   ├── PaymentServiceTest.java
│   ├── FeedbackServiceTest.java
│
│── Dockerfile
│── pom.xml
│── README.md
```
## **Contributors**
```
Aangi Shah: 202412089
Vraj Sanghavi: 202412086
Ayush Shah: 202412092
Tanya Jhaveri: 202412027
```
