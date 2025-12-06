#FROM eclipse-temurin:17-jdk-jammy
#WORKDIR /app
#COPY target/*.jar app.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Install Maven inside container
RUN apt-get update && apt-get install -y maven
# Copy root parent's pom for multi-module resolution
COPY pom.xml .
# Download dependencies (cached layer)
# Mount your source code when running (done via docker-compose)
EXPOSE 8083
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]

