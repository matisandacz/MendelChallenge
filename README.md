# MendelChallenge

## Swagger UI
- Access the Swagger UI at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

![Example Image](https://github.com/matisandacz/MendelChallenge/blob/main/images/OpenAPI.PNG)

## OpenAPI JSON Specification
- Get the OpenAPI JSON Specification at: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Code Formatting
To apply code formatting using Gradle, run the following command:
```bash
./gradlew spotlessApply
```

## Build Docker Image
To build the Docker image, use the following commands:
```bash
./gradlew build
docker build -t mendel/challenge .
docker run -p 8080:8080 mendel/challenge
```