FROM adoptopenjdk/openjdk11:latest
ADD target/Distributor.jar Distributor.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar",  "Distributor.jar"]