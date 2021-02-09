FROM openjdk:14-jdk-alpine
WORKDIR /engineer_review
COPY ./build/libs/engineer_review-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["kotlin", "java","-jar","/app.jar"]