FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY ./target/monitoring-communication-0.0.1-SNAPSHOT.jar /app
EXPOSE 8083

CMD ["java", "-jar", "monitoring-communication-0.0.1-SNAPSHOT.jar"]