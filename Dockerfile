FROM adoptopenjdk/openjdk11:latest
ADD target/Distributor.jar Distributor.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar",  "Distributor.jar"]