FROM openjdk:23-oraclelinux9
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]