FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build --no-daemon

RUN cp build/libs/*.jar app.jar

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=builder /app/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
