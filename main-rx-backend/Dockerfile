FROM adoptopenjdk/openjdk11:ubi

RUN mkdir /app
WORKDIR /app

COPY . ./

RUN ./mvnw clean install

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]
EXPOSE 8080
