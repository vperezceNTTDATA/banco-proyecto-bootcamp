FROM openjdk:17-oracle
COPY "./target/api-gateway-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8011
ENTRYPOINT [ "java", "-jar", "app.jar" ]