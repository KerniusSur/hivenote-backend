# Hivenote Backend

This is the backend for the Hivenote project. It is a RESTful API built with Java and Spring Boot.

## Run the project locally

#### 1. Clone the repository
#### 2. Run the project:

a. By running the following command, and providing the required environment variables from AWS:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--aws.accessKey=<your-access-key> --aws.secretAccessKey=<your-secret-access-key> --aws.bucketName=<your-bucket-name> --aws.isDisabled=false"
```

b. By running the following command, if you don't have AWS credentials and want to run the project without AWS services:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--aws.isDisabled=true"
```
_**Note**: running the application server this way will disable the AWS services, including file upload/download functionality._

#### 3. The application server will be running on `http://localhost:8080`
