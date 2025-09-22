FROM eclipse-temurin:21-jdk as builder

WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY . .

RUN ./gradlew build --no-daemon
RUN cp $(ls build/libs/*.jar | grep -v plain) /app/app.jar

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=builder /app/app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
