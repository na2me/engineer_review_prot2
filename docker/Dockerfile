FROM openjdk:14-jdk-alpine
RUN mkdir /app
WORKDIR /app

ENV JAR_TARGET "engineer_review-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["sh","-c","java -jar -Dspring.profiles.active=docker-compose ./build/libs/${JAR_TARGET}"]