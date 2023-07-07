FROM openjdk:17-oracle
COPY "./target/discovery-service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8761
ENTRYPOINT [ "java", "-jar", "app.jar" ]