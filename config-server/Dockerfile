FROM openjdk:17-oracle
COPY "./target/config-server-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8888
ENTRYPOINT [ "java", "-jar", "app.jar" ]