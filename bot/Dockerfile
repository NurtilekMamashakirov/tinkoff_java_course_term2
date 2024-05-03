FROM openjdk:21
COPY . /target/bot.jar
WORKDIR /target/bot.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "target/bot.jar"]
