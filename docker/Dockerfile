FROM openjdk:14-jdk-alpine
RUN mkdir /app
WORKDIR /app
VOLUME /app
ENV JAR_TARGET "engineer_review-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["sh","-c","java -jar ./build/libs/${JAR_TARGET}"]