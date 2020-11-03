FROM java:8

MAINTAINER finlu <finlu.jzw@gmail.com>

ADD target/food-0.0.1-SNAPSHOT.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
