# Spring-Boot-kafka-simple

### Run the Docker compose file
```
docker-compose up -d
```
### Run the Spring Boot application

```
Either build the project or open target folder and execute the jar

java -jar target/kafka-sample1-1.0.jar
```

### Send JSON data to spring-boot server
```
Method Type -  POST
URL: http://localhost:8080/send/user

JSON format
{
  "user" : "Jitu",
  "email" : "jitunayak715@gmail.com"
}
```
