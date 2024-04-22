# Student Portal

This is a Spring Boot application developed in Java and built with Maven. It's a web application that provides a platform for students to manage their profiles, view courses, and check their graduation eligibility.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java
- Maven
- Docker
- Docker Compose
- IntelliJ IDEA 2023.3.6 or any preferred IDE

#### Finance
The application must integrate with the Finance microservice, available through the following repository: 
``` https://github.com/tvergilio/finance ```
 
#### Library
The application must integrate with the Library microservice, available through the following repository: 
``` https://github.com/tvergilio/CESBooks ```
 
Note: as of 2024 there has been an issue with sqlalchemy library (see Discord). To correct this follow these steps:
1. Clone the repo (https://github.com/tvergilio/CESBooks)
2. Rename .env.example to .env
3. Update dependency to cs50~=9.3.0 in requirements.txt file
4. Run docker build --tag ces-books:4.0 . in terminal to create a new Docker image
5. Update docker-compose.yml file to use the image that we just created:
** docker-compose.yml**
```
libraryapp:
  image: ces-books:4.0
```
6. Run docker-compose up -d in terminal now
7. The library app should work fine now at localhost:80!

### Building the Project

1. Clone the repository
2. Open the project in your IDE
3. Run the following command to build the project with Maven:

```bash
mvn clean install
```

### Deployment
This application can be deployed using Docker and Docker Compose. Here are the steps to do so:
Build the Docker image for the application:
```
docker-compose up -d --build
```

Your application should now be running at http://localhost:8083   

### Usage

The application provides the following features:  
- Home: The main landing page of the application.
- View Courses: Allows students to view available courses.
- Graduation: Displays the graduation eligibility status of the student.
- Profile: Allows students to view and update their profile.
- Logout: Logs out the user from the application.

### Built With

Java - The programming language used
Spring Boot - The framework used
Maven - Dependency Management

### Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.  

### Authors
Bryan Gomez - Leeds Beckett Univeristy Student 
 
### License
This project is licensed under the MIT License - see the LICENSE.md file for details
 
