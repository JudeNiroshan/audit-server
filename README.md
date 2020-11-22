# Audit Server 🛂

[![Build Status](https://travis-ci.org/JudeNiroshan/audit-server.svg?branch=master)](https://travis-ci.org/JudeNiroshan/audit-server)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/39a629cee58f448b8b3acbc565d896e4)](https://app.codacy.com/manual/JudeNiroshan/audit-server?utm_source=github.com&utm_medium=referral&utm_content=JudeNiroshan/audit-server&utm_campaign=Badge_Grade_Dashboard)
[![codecov](https://codecov.io/gh/JudeNiroshan/audit-server/branch/master/graph/badge.svg)](https://codecov.io/gh/JudeNiroshan/audit-server)
[![Known Vulnerabilities](https://snyk.io/test/github/JudeNiroshan/audit-server/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/JudeNiroshan/audit-server?targetFile=pom.xml)
[![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/juden/audit-server?sort=date)](https://hub.docker.com/repository/docker/juden/audit-server)
[![Docker Pulls](https://img.shields.io/docker/pulls/juden/audit-server)](https://hub.docker.com/repository/docker/juden/audit-server)

audit-server is a spring-boot REST API which accepts user events as JSON objects
and delegates them to a remote server to handle it appropriately.

Sample JSON payload _(endpoint: `/api/user/event`, Method: `POST`)_ :
```
{
 "timestamp": 15623276532,
 "userId": 1029,
 "event": "something happened. Please log this in a file"
}
```


### Configurations 🛠️


- Eureka server endpoint in `application.properties` _(Default `http://localhost:8761/eureka`)_

### How to run 🏃🏽‍♂️


#### Prerequisite:
Ensure [logger-eureka-server](https://github.com/JudeNiroshan/logger-eureka-server) 
instance is up and running on your local machine. Then,

 - clone the repository to your machine [`git clone https://github.com/JudeNiroshan/audit-server.git`]
 - move to `audit-server` [`cd audit-server`]
 - execute `./mvnw install`
 - execute `./mvnw spring-boot:run` (submit **POST** request to `http://localhost:8080/api/user/event`)

### Developer Notes


- Application is using [google protocol buffers](https://developers.google.com/protocol-buffers) as protocol for calling remote servers. 
There are several frameworks available which uses `.protoc` as protocol. By default 
audit-server is using [gRPC](https://grpc.io/). It is possible to change it by implementing `Meditator` interface 
and configuring relevant type in `application.properties` file. _(Default `gpb.type=grpc`)_
- audit-server is performing application load-balancing by using 
 [client service discovery](https://microservices.io/patterns/client-side-discovery.html) 
 through an Eureka server.
