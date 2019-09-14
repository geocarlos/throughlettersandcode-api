FROM openjdk:10-slim
ADD target/through-letters-and-code-api-1.0.0-SNAPSHOT.jar tlc.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tlc.jar"]
