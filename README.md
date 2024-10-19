# Expense tracking application

![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
![](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)

The application “Expense tracking” can be utilized by users in order to track personal finance situation: expenses and income, as well operations across various accounts (bank cards, cash etc.)

## Project description

The target audience of this project are people who want to track their expenses and income and have an overview of the current financial situation. They are not satisfied by existing solutions and want to have more clear and user friendly interface and synchronization between different devices (web, mobile). The usage of the project provides following of benefits: operations are categorized and recorded in a clear manner; different reports are generated to present an actual situation etc.
From a domain driven design perspective, the application is divided into 4 core domains: Users, Payment accounts, Categories, Operations.

## Installation instructions

The application is implemented using a monolith design and is packed as a standard fat jar Java application - that means it can be executed at any environment that supports Java 21. In order to run the application, you first have to _build it_. As frontend and backend are bundled in the single Jar file, you have a single artifact to run.

In order to build the application, please make sure that you have following components installed before building the project from source:

- JDK 21 or higher
- Maven

The next step is to prepare the PostgreSQL database instance. You need to provide following environemnt variables:

- ```DATABASE_URL``` - the [JDBC url](https://www.baeldung.com/java-jdbc-url-format) for your database
- ```DATABASE_USER``` - the username
- ```DATABASE_PASSWORD``` - the password

You need to complete these steps to build the project:

1. Navigate to the root project folder
2. Execute ```mvn clean install``` in your terminal

In order to run the project:

1. Navigate to the root project folder
2. Run ```java -jar -Dspring.profiles.active=prod -DDATABASE_URL={jdbc_url} -DDATABASE_USER={user} -DDATABASE_PASSWORD={password} target/expense-tracking-0.0.1-SNAPSHOT.jar``` in your terminal
3. Navigate to [http://localhost:8080](http://localhost:8080) in your browser

## Author

(C) 2024 Iurii Mednikov

The code is delivered under terms of the MIT software license. For more information, check the ```LICENSE.txt``` file.