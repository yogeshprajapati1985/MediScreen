# MediScreen
MediScreen springboot application.

## Project Overview
MediScreen is specializing in detecting risk factors for diseases such as diabetes.

## Features
- Patient Demographics APIs
- Patient History / Note APIs
- Diabetes Assessment API
- Springboot Endpoints: Implement endpoints for health, info and metrics.
- JSON Output: Produce JSON output from corresponding URLs.
- MVC Design Pattern: Architect SafetyNet Alerts following the Model-View-Controller design pattern while adhering to SOLID principles.

## Technologies Used
- Java
- Spring Boot
- Maven
- RESTful API
- JSON
- Docker (optional)

## Getting Started
To get started with the SafetyNet Spring Boot project, follow these steps:

### 1. Clone the Repository
Clone the repository to your local machine using Git:
```bash
git clone https://github.com/yogeshprajapati1985/mediscreen.git
```

### 2. Install Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven 3.6+
- Git
- Docker

### 3. Build the Jar
Navigate to the project directory and run the following command to build the project:
```bash
mvn clean package
```
This command will compile the code, run tests, and package the application into a JAR file.

### 4. Build the Docker Image
You can build a Docker image for the application. Ensure you have Docker installed and running, then execute the following command in the project root directory:
```bash
docker build -t mediscreen .
```

#### 5. Run the compose.yml
If you have Docker Compose installed, you can run the application using the provided `compose.yml` file. Execute the following command:
```bash
docker-compose -f compose.yml up --build
```

### 6. Access the Application
Once running, the application will be available at:
http://localhost:8080