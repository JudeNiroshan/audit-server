FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Jude Niroshan

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/auditserver-1.0.0.jar /app/
ENTRYPOINT ["java", "-jar", "auditserver-1.0.0.jar"]
