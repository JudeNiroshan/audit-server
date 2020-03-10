FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Jude Niroshan

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN bash -c ' \
    apk update && apk --no-cache add ca-certificates wget && \
    wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.31-r0/glibc-2.31-r0.apk && \
    apk add glibc-2.31-r0.apk \
    '
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/auditserver-1.0.0.jar /app/
ENTRYPOINT ["java", "-jar", "auditserver-1.0.0.jar"]
