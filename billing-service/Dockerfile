FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY build/libs/billing-service.jar app.jar
EXPOSE 4001 9001
ENTRYPOINT ["java","-jar","/app/app.jar"]
