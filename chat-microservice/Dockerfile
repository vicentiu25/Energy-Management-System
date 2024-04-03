FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY ./target/chat-microservice-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "chat-microservice-0.0.1-SNAPSHOT.jar"]