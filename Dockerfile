# Step 1: Use Java 17 image from Eclipse Temurin (official OpenJDK)
FROM eclipse-temurin:17-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy everything from your project into the container
COPY . .

# Step 4: Build your Spring Boot app inside the container using Maven Wrapper
# We skip tests for speed in Sprint 1
RUN ./mvnw -DskipTests clean package

# Step 5: Expose port 8080 so we can access the app
EXPOSE 8080

# Step 6: Run the JAR that was built
# IMPORTANT: make sure this filename matches the JAR in your /target folder after build
CMD ["java", "-jar", "target/cookiegram-0.0.1-SNAPSHOT.jar"]
