FROM adoptopenjdk/openjdk11:alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /home/app/

COPY target/*.jar app.jar

CMD [ "java","-jar","app.jar" ]