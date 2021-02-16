FROM openjdk:14-jdk-alpine
RUN mkdir /spring
WORKDIR /spring
VOLUME /spring
ENV JAR_TARGET "engineer_review-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["sh","-c","java -jar back_end/build/libs/${JAR_TARGET}"]