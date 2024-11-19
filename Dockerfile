FROM openjdk:21-jdk-alpine
COPY "./target/bankapp.jar" "app.jar"
EXPOSE 3000
ENTRYPOINT ["java","-jar","app.jar"]