# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven metadata first to leverage caching
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Now copy source and build
COPY src ./src
RUN mvn -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy the fat jar produced by Spring Boot
COPY --from=build /app/target/*.jar app.jar

# Koyeb injects PORT; Spring should read it
# (ensure in application.properties: server.port=${PORT:8080})
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
