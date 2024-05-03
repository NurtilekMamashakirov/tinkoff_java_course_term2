FROM openjdk:21
COPY . /target/scrapper.jar
WORKDIR /target/scrapper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/scrapper.jar"]
