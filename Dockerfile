FROM openjdk:17-jdk-alpine
WORKDIR /app
ADD target/TrendistaShop.jar /app/TrendistaShop.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app/TrendistaShop.jar"]