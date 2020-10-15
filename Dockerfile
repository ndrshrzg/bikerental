FROM openjdk:11-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY /build/libs/bikerental-0.1.jar /app/bikerental-app.jar

ENTRYPOINT ["java", "-jar","/app/bikerental-app.jar"]