# [ms-shopping-basket-management](https://github.com/kevinxjavier/ms-shopping-basket-management.git)

## Project for securitize SpringBoot project 

##### Software Requirements:
* Apache Maven 3.8.6
* Java 18.0.2.1
* PostgreSQL 13.14
* Docker 24.0.7
* Docker Compose version 2.24.6
* Spring-boot 3.3.4
* Tested on Debian GNU/Linux 9 (stretch)
* Install [core-parent](https://github.com/kevinxjavier/core-parent.git)

##### Introduction

Its responsibilities are the following:
* Shopping Basket Management Service

##### Setup

###### 1) Docker Postgres
Start a Postgres instance: 
```
$ sudo docker run --name postgres -e POSTGRES_DB=shopping -p 5432:5432 -e POSTGRES_PASSWORD=123456789 -e POSTGRES_USER=administrator -d postgres:13-alpine
```

##### Compile (If problems)
```
$ mvn clean compile package
$ mvn clean compile package -U       # -U means force update of snapshot dependencies

# If your local repository is somehow mucked up for release jars as opposed to snapshots (-U and --update-snapshots only update snapshots), you can purge the local repo using the following:
$ mvn dependency:purge-local-repository

# You probably then want to clean and install again (to solve the problem):
$ mvn dependency:purge-local-repository clean install
```

###### 2) Install Microservice
Simply move to the project's root folder and run from a terminal:
`mvn clean install`

###### 3.1) Run
Starting from the project's root folder, execute the following steps from a new terminal:
`mvn spring-boot:run -f ms-shopping-basket-management-boot/pom.xml`

###### 3.2) Run Docker
These are the spteps to create and run microservice in a Docker, starting from the project's root folder execute:
```
$ sudo docker build -t kevinpina/shopping-basket . --no-cache=true    
$ sudo docker run --name shopping -p 8080:8080 -d kevinpina/shopping-basket

# Note: Remember to change the ip and credentials in PostgreSQL in file: 
        $ ls -l ./ms-shopping-basket-management-boot/src/main/resources/application.yml
        in spring.datasource.url spring.datasource.username and spring.datasource.password
        
        And finally due to Dockerfiles is not able to parse or read this variables database-url, 
        database-user, database-passin the application.yml replace the with literal values this 
        properties: spring.datasource.url spring.datasource.username and spring.datasource.password
        i.e.: spring.datasource.url = "jdbc:postgresql://192.168.50.62:5432/shopping"
        instead of: spring.datasource.url = *database-url
```

###### 3.3) Run Docker Compose
After created the docker kevinpina/shopping-basket:latest we can run multiple instances of the app dockerized, 
Starting from the project's root folder execute: 
```
$ sudo docker compose build
$ sudo docker compose up --scale app=3 -d

# To watch the ports assign execute:
# sudo docker compose ps
```

###### Swagger
- http://localhost:8080/swagger-ui/index.html

##### Usage
See postman repository [Postman Collection APIRest](https://github.com/kevinxjavier/postman-apirest.git). Token Folder, request Signup and Login.

##### Contact

| User    | Name                     | Role       |
|:--------|:-------------------------|:-----------|
| kpina   | Kevin Javier Piña        | Developer  |
