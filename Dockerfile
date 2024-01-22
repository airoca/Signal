FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar springboot-example.jar
EXPOSE 8080
CMD ["java", "-jar", "springboot-example.jar"]