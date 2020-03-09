## Audit Server


[![Codacy Badge](https://api.codacy.com/project/badge/Grade/39a629cee58f448b8b3acbc565d896e4)](https://app.codacy.com/manual/JudeNiroshan/audit-server?utm_source=github.com&utm_medium=referral&utm_content=JudeNiroshan/audit-server&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/JudeNiroshan/audit-server.svg?branch=master)](https://travis-ci.org/JudeNiroshan/audit-server)
[![codecov](https://codecov.io/gh/JudeNiroshan/audit-server/branch/master/graph/badge.svg)](https://codecov.io/gh/JudeNiroshan/audit-server)
![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/juden/audit-server?sort=date)

audit-server is a REST API which accept user events as JSON objects
and delegates to gRPC remote server to handle it appropriately. This 
server is the gateway to interact with end users. (API developers)

Sample JSON payload _(endpoint: `/api/user/event`)_ :
```
{
 "timestamp": 15623276532,
 "userId": 1029,
 "event": "something happened. Please log this in a file"
}
```
### How to run 🏃🏽‍♂️

#### Prerequisite:
Ensure [logger-eureka-server](https://github.com/JudeNiroshan/logger-eureka-server) instance up and running on your local machine. Then,

 - clone the repository to your machine [`https://github.com/JudeNiroshan/audit-server.git`]
 - move to `audit-server` [`cd audit-server`]
 - execute `./mvnw install`
 - execute `./mvnw spring-boot:run` (submit POST request to `http://localhost:8080/api/user/event`)
 
### Insights

 - Currently audit-server using client service discovery with a 
 Eureka server. Basically audit-server will do application 
 load-balancing.

