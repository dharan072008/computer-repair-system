# 💻 Computer Repair and Service Tracking System

A full-stack, enterprise-grade **Computer Repair & Service Tracking Management System** developed with **Java 21/26**, **Spring Boot 4**, **Spring Data JPA (Hibernate)**, and **SQL (MySQL & Embedded H2)**, paired with a modern, responsive web dashboard.

Designed specifically for academic project evaluations, college lab assessments, and OOPJ (Object-Oriented Programming using Java) viva examinations.

---

## 🌟 Key Features

- 📝 **Repair Ticket Intake**: Register repair requests with customer details, phone number, device type (Laptop, Desktop, Mac, Server), and detailed hardware/software problem descriptions.
- 📊 **Real-Time Analytics Dashboard**: Live metrics summary cards displaying **Total Tickets**, **Registered**, **In-Progress**, **Waiting for Parts**, **Completed**, and **Cancelled** repairs.
- 🔍 **Live Search & Filter**: Instant client-side search across customer name, device type, phone, and technician, alongside status dropdown filtering.
- 🔄 **Status & Technician Workflow**: Seamlessly assign technicians and update repair status from intake through diagnostic, parts procurement, and final delivery.
- 🗑️ **Safe Record Deletion**: Confirmation modal safeguards against accidental deletion of repair records.
- 🗄️ **Dual Database Architecture**:
  - **Embedded H2 Database (Default)**: Zero-configuration, file-backed database (`./data/repair_db`) that runs anywhere without requiring external server installations.
  - **MySQL Server 8.x**: Production-ready relational storage on `localhost:3306` with automatic schema generation (`computer_repair_db`).
- 🖥️ **Interactive H2 Web Console**: Inspect tables, test raw SQL queries, and verify persistence at `http://localhost:8080/h2-console`.
- 🌐 **Modern Responsive Web UI**: Clean glassmorphism styling, interactive modals, non-blocking toast notifications, and dark accents.

---

## 🛠️ Technology Stack

| Layer | Technologies |
|---|---|
| **Backend Framework** | Java 21/26, Spring Boot 4.x |
| **Persistence / ORM** | Spring Data JPA, Hibernate 6.x |
| **Databases** | H2 Database (File-backed `./data/repair_db`), MySQL 8.x |
| **Build & Dependency Tool** | Apache Maven |
| **Frontend** | HTML5, Modern CSS3 (Glassmorphism & Flexbox/Grid), Vanilla JavaScript (ES6+ Fetch API) |
| **Testing** | JUnit 5, AssertJ, Spring Boot Test |

---

## 🧠 Object-Oriented Programming (OOP) Concepts Demonstrated

This project adheres strictly to core Object-Oriented design principles for OOPJ evaluation:

1. **Encapsulation**:
   - The [`Repair`](file:///c:/Users/dhara_v71lkgx/OneDrive/Desktop/computer%20repair%20management/src/main/java/com/repair/tracking/model/Repair.java) class encapsulates entity properties (`customerName`, `deviceType`, `repairStatus`, etc.) as `private` fields.
   - Access and state mutations are strictly managed through validated public getters and setters.
2. **Abstraction**:
   - Database operations are abstracted away from business logic via [`RepairRepository`](file:///c:/Users/dhara_v71lkgx/OneDrive/Desktop/computer%20repair%20management/src/main/java/com/repair/tracking/repository/RepairRepository.java), extending Spring Data's `JpaRepository`.
   - The Service layer hides persistence complexities from the REST Controllers.
3. **Separation of Concerns (Layered Architecture)**:
   - **Controller Layer** (`RepairController`): Handles incoming HTTP requests, validates inputs, and formats RESTful JSON responses.
   - **Service Layer** (`RepairService`): Encapsulates business logic, data transformation, and metrics calculation.
   - **Data Access Layer** (`RepairRepository`): Manages persistence with SQL/ORM.
   - **Model / Entity Layer** (`Repair`): Represents database table schema.
   - **DTO Layer** (`RepairStatsDTO`): Decouples analytics aggregation from database entities.
4. **Polymorphism & Constructor Overloading**:
   - Both default (no-arg) and parameterized overloaded constructors in [`Repair`](file:///c:/Users/dhara_v71lkgx/OneDrive/Desktop/computer%20repair%20management/src/main/java/com/repair/tracking/model/Repair.java) and [`RepairStatsDTO`](file:///c:/Users/dhara_v71lkgx/OneDrive/Desktop/computer%20repair%20management/src/main/java/com/repair/tracking/dto/RepairStatsDTO.java).

---

## 🗂️ Project Structure

```
computer repair management/
├── pom.xml                                    # Maven configuration & dependencies
├── Readme.md                                  # Complete project documentation
├── index.html                                 # Standalone frontend entry point
├── idex.html                                  # Alternative standalone frontend entry point
├── data/
│   └── repair_db.mv.db                        # Persistent H2 database storage
├── src/
│   ├── main/
│   │   ├── java/com/repair/tracking/
│   │   │   ├── RepairTrackingApplication.java # Spring Boot main entry point
│   │   │   ├── controller/
│   │   │   │   └── RepairController.java      # REST API endpoints & CORS handling
│   │   │   ├── dto/
│   │   │   │   └── RepairStatsDTO.java        # Dashboard metrics DTO
│   │   │   ├── model/
│   │   │   │   └── Repair.java                # JPA Entity representing a repair ticket
│   │   │   ├── repository/
│   │   │   │   └── RepairRepository.java      # JPA Repository with custom query methods
│   │   │   └── service/
│   │   │       └── RepairService.java         # Business logic layer
│   │   └── resources/
│   │       ├── application.properties         # Primary Spring Boot configuration
│   │       ├── application-h2.properties      # Zero-config H2 profile configuration
│   │       ├── application-mysql.properties   # MySQL Server profile configuration
│   │       └── static/
│   │           └── index.html                 # Embedded static web dashboard
│   └── test/
│       └── java/com/repair/tracking/
│           └── RepairTrackingApplicationTests.java # Automated JUnit integration tests
```

---

## 🧱 Database Schema

Table name: `repairs`

```sql
CREATE TABLE repairs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    device_type VARCHAR(50) NOT NULL,
    problem_description TEXT,
    technician VARCHAR(100),
    repair_status VARCHAR(30) DEFAULT 'REGISTERED',
    repair_date DATE
);
```

### Valid Status Values:
- `REGISTERED` (Initial intake)
- `IN_PROGRESS` (Active diagnostic / service)
- `WAITING_FOR_PARTS` (Awaiting hardware components)
- `COMPLETED` (Repaired and ready for pickup)
- `CANCELLED` (Customer cancelled / unrepairable)

---

## ⚙️ REST API Endpoints

All endpoints support JSON format and are CORS-enabled:

| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| **GET** | `/api/repairs` | Fetch all repairs (most recent first) | None |
| **GET** | `/api/repairs?status=IN_PROGRESS` | Filter repairs by status | None |
| **GET** | `/api/repairs/{id}` | Retrieve single repair ticket by ID | None |
| **GET** | `/api/repairs/stats` | Retrieve aggregated dashboard counts | None |
| **POST** | `/api/repairs` | Register a new repair ticket | JSON `Repair` object |
| **PUT** | `/api/repairs/{id}` | Update existing repair ticket | JSON `Repair` object |
| **DELETE** | `/api/repairs/{id}` | Delete repair ticket by ID | None |

### Sample POST / PUT Request Payload:
```json
{
  "customerName": "John Doe",
  "phone": "+1-555-0199",
  "deviceType": "MacBook Pro M2",
  "problemDescription": "Liquid spilled on keyboard, trackpad unresponsive.",
  "technician": "Alex Rivera",
  "repairStatus": "IN_PROGRESS",
  "repairDate": "2026-10-28"
}
```

---

## ▶️ How to Run the Application

### Option A: Using the Embedded H2 Database (Recommended - Zero Setup)

1. Open a terminal in the project root directory.
2. Set `JAVA_HOME` if needed and run:
   ```powershell
   mvn spring-boot:run
   ```
3. Open your browser and navigate to:
   - **Web Application**: `http://localhost:8080/`
   - **H2 Web Console**: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:file:./data/repair_db`
     - User Name: `sa`
     - Password: *(leave blank)*

### Option B: Using MySQL 8.x

1. Ensure MySQL Server is running on port `3306`.
2. Open `src/main/resources/application.properties` and switch the profile:
   ```properties
   spring.profiles.active=mysql
   ```
3. Adjust credentials in `src/main/resources/application-mysql.properties` if needed:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```
4. Run the project:
   ```powershell
   mvn spring-boot:run
   ```
   *The database `computer_repair_db` and tables will be created automatically.*

---

## 🧪 Running Automated Tests

Run the automated JUnit 5 test suite:
```powershell
mvn test-compile
```
Tests cover Spring Application context loading, CRUD operations, DTO aggregation, and REST Controller HTTP status and validation logic.

---

## 👨‍💻 Author & Academic Information

- **Project**: Computer Repair & Service Tracking System
- **Course**: Object-Oriented Programming using Java (OOPJ)
- **Architecture**: Spring Boot RESTful Web Service + MVC JPA Architecture + SPA Frontend
