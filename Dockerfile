FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/CYR-backend-1.0.0.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

