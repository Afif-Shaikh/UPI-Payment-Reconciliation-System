#FROM eclipse-temurin:17-jdk-jammy
#WORKDIR /app
#COPY target/*.jar app.jar
#EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Install Maven inside container
RUN apt-get update && apt-get install -y maven
# Copy root parent's pom for multi-module resolution
COPY pom.xml .
# Download dependencies (cached layer)
#RUN mvn -q dependency:go-offline
# Mount your source code when running (done via docker-compose)
# DO NOT COPY code here
EXPOSE 8082
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]
#CMD ["mvn", "spring-boot:run", "-pl", "transaction-service", "-am", "-Dspring-boot.run.fork=false"]
