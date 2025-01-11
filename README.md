# Hướng dẫn sử dụng ứng dụng TrendistaShop

## Mô tả
Ứng dụng TrendistaShop là một ứng dụng web được xây dựng bằng Spring Boot và sử dụng MySQL làm cơ sở dữ liệu. Dự án này được triển khai trong môi trường Docker, giúp dễ dàng triển khai và chạy trên các máy khác nhau.

## Yêu cầu
Trước khi bắt đầu, bạn cần cài đặt các công cụ sau trên máy tính của mình:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Các bước cài đặt và chạy ứng dụng

### 1. Clone repository về máy
### 2. Build và chạy ứng dụng với Docker Compose
Sau khi clone xong, bạn có thể build và chạy ứng dụng với Docker Compose. Dự án đã được cấu hình sẵn với Docker Compose, vì vậy bạn chỉ cần chạy lệnh sau:
```
mvn clean package -DskipTests
```
- Để build file Jar cho project
```
docker-compose up -d --build
```
- Để build docker container/image

Lệnh trên sẽ thực hiện các công việc sau:

- Tải về các image cần thiết (MySQL, Spring Boot).
- Tạo và khởi động các container cho MySQL và Spring Boot.
- Backup database cho docker container 
  MySQL sẽ chạy trên cổng 3308, và ứng dụng Spring Boot sẽ chạy trên cổng 8080.
### 3. Truy cập ứng dụng
- MySQL sẽ chạy trên cổng 3308 của máy chủ local (localhost:3308).
- Spring Boot sẽ chạy trên cổng 8080 của máy chủ local (localhost:8080).
- Truy cập link to API Swagger(http://localhost:8080/swagger-ui/index.html)
