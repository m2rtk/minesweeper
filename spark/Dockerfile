FROM openjdk:jre-slim

COPY target/spark-latest.jar server.jar

EXPOSE 8080

CMD ["java", "-jar", "server.jar"]
