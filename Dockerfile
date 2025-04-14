#FROM ubuntu:latest
#
#FROM  openjdk
#
#LABEL authors="shivam dubey"
#
#COPY target/*.jar /employee-management-service.jar
#
#ENTRYPOINT ["java", "-jar", "/employee-management-service.jar"]
#
#EXPOSE 8080


# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /src
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/*.jar /employee-management-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/employee-management-service.jar"]


