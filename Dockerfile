FROM openjdk:11
ADD build/libs/library.jar library.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","library.jar"]

