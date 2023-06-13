FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn clean package -DskipTests
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

FROM ghcr.io/graalvm/graalvm-ce:22.3.1
COPY --from=build /app/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar","com.example.demo.DemoApplication"]