FROM openjdk:14-jdk-alpine
WORKDIR /back_end
ENV JAR_TARGET "engineer_review-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["sh","-c","java -jar build/libs/${JAR_TARGET}"]