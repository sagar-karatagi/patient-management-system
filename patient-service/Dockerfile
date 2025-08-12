FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# copy the locally built jar (make sure jar is in build context)
COPY build/libs/patient-service.jar app.jar

EXPOSE 4000

ENTRYPOINT ["java","-jar","/app/app.jar"]
