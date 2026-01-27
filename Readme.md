🧳 Campus Lost & Found Management System

A real-time Campus Lost & Found Management System developed using Spring Boot and SQL Database.
This project helps students and staff report lost or found items and retrieve them efficiently using a centralized digital platform.

🚀 Features

📌 Report Lost or Found items

📊 Real-time data storage using SQL database

🔍 View all lost & found items with filtering

🔗 REST API integration for frontend support

🧩 Clean and scalable Spring Boot architecture

🌐 CORS-enabled backend for web frontend

🕒 Latest items shown first

🛠️ Tech Stack
Backend

Java

Spring Boot

Spring Data JPA

REST APIs

Database

MySQL (or any SQL-based database)

Frontend

HTML

Tailwind CSS

JavaScript

🗂️ Project Structure
src/main/java/com/college/lostfound
│
├── LostFoundApplication.java
├── Item.java
├── ItemService.java
├── ItemController.java
├── ItemRepository.java
│
resources/
├── application.properties

🧱 Database Schema
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    location VARCHAR(255),
    type ENUM('LOST','FOUND'),
    contact VARCHAR(150),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

⚙️ API Endpoints
Method	Endpoint	Description
GET	/items	Fetch all items
GET	/items?type=LOST	Fetch lost items
GET	/items?type=FOUND	Fetch found items
GET	/items/{id}	Get item by ID
POST	/items	Add a new item
▶️ How to Run the Project

Clone the repository:

git clone https://github.com/your-username/campus-lost-found.git


Configure database in application.properties

Run the Spring Boot application:

mvn spring-boot:run


Open in browser:

http://localhost:8080/items

🎯 Use Case

College campuses

Hostels

Schools

Public institutions

Hackathons & academic projects

📌 Future Enhancements

🔐 User authentication

💬 In-app chat between finder & owner

📱 Mobile app support

🤖 AI-based item matching

📸 Image upload support

👨‍💻 Developed By

Kishor Kumar
Mini Project | Java & Spring Boot