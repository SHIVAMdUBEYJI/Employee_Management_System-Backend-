FROM ubuntu:latest

FROM  openjdk

LABEL authors="shivam dubey"

COPY target/*.jar /employee-management-service.jar

ENTRYPOINT ["java", "-jar", "/employee-management-service.jar"]

EXPOSE 8080;

