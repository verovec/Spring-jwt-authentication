# Spring security - JWT implementation

# Stack

![](https://img.shields.io/badge/java_11-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)


# File structure

```
spring-boot-jwt/
 │
 ├── src/main/java/com/quest/
 │   └── etna
 │       ├── config
 │       │   ├── JwtAuthenticationEntryPoint.java
 |       |   ├── JwtRequestFilter.java
 |       |   ├── JwtTokenUtil.java
 |       |   └── WebSecurityConfig.java
 │       │
 │       ├── controller
 |       |   ├── AuthenticationController.java
 │       │   └── DefaultController.java
 │       │
 │       ├── model
 │       │   ├── JwtUserDetails.java
 |       |   ├── Role.java
 │       │   └── User.java
 │       │
 │       ├── repository
 │       │   └── UserRepository.java
 │       │
 │       ├── service
 │       │   ├── IModelService.java
 │       │   ├── JwtUserDetailsService.java
 │       │   └── UserService.java
 │       │
 │       └── QuestWebJavaApplication.java
 │
 ├── src/main/resources/
 │   └── application.properties
 |
 ├── mvnw/mvnw.cmd
 └── pom.xml
```

### DESCRIPTION :

This project show you how to setup registration and authentication with JWT in spring-boot

### HOW TO :

- Launch mysql
> docker-compose up -d database adminer
Then go on http://localhost:8080 to manage mysql
```
Creds : {
    "username": "application",
    "password": "password",
    "host": "database",
    "db name": "quest_web",
}
```

- Run project
> ./mvnw spring-boot:run


### DOCUMENTATION :

| function             | Route              | Status Code              |
| -------------------- | ------------------ | ------------------------ |
| testSuccess()        | */testSuccess*     | 200 OK                   |
| testNotFound()       | */testNotFound*    | 404 NOT FOUND            |
| testError()          | */testError*       | 500 INTERNAL SERVER ERROR|


| Method | Params                           | Response Body  | Route           | Status Code Success | Status Code Error  |
| ------ | -------------------------------- | -------------- | --------------- | ------------------- | ------------------ |
| POST   | {"username": "", "password": ""} | ModelUser      | */register*     | 201 CREATED         | 400 BAD REQUEST    |
| POST   | {"username": "", "password": ""} | {"token": ""}  | */authenticate* | 200 OK              | 401 UNAUTHORIZED   |
| GET    |                 /                | ModelUser      | */me*           | 200 OK              | 401 UNAUTHORIZED   |
