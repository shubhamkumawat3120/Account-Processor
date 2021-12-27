FROM openjdk:11
WORKDIR /app
EXPOSE 8070
ADD ./target/Account-Processor-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]