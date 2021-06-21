FROM openjdk:15-jdk-alpine

RUN apk update && apk add wget
RUN mkdir /opt/gradle
RUN wget https://services.gradle.org/distributions/gradle-7.1-bin.zip
RUN unzip -d /opt/gradle gradle-7.1-bin.zip
ENV PATH="/opt/gradle/gradle-7.1/bin:${PATH}"

COPY . /project
RUN cd /project && gradle build -x test

ENTRYPOINT ["java", "-jar", "/project/build/libs/planman-0.0.1-SNAPSHOT.jar"]