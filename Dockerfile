FROM openjdk:17-jdk-alpine
WORKDIR /app
ADD target/TrendistaShop.jar /app/TrendistaShop.jar
ENTRYPOINT ["java", "-jar", "/app/TrendistaShop.jar"]