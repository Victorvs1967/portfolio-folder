FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine

ADD . /app
WORKDIR /app

RUN ./mvnw clean package -DskipTests
RUN mv target/main-rx-backend-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]