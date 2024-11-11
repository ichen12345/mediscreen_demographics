FROM openjdk:17-jdk-alpine
<<<<<<< HEAD
RUN addgroup -S app && adduser -S app -G app
USER app
=======
>>>>>>> a9997c88ad2664a8b75407de7db13e726ab4fdcf
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]