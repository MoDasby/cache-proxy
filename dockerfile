FROM maven:3.9.9 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/target/cache-proxy-*.jar /app/cache-proxy.jar
COPY src/main/resources/application.properties /app/config/application.properties

EXPOSE 8080

ENV SPRING_CONFIG_LOCATION=file:/app/config/application.properties

CMD ["java", "-jar", "/app/cache-proxy.jar"]