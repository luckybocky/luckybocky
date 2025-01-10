# Dockerfile for Spring Boot application using Gradle
FROM openjdk:17-jdk-alpine AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper와 빌드 파일 복사
COPY gradlew .
COPY gradle/wrapper/ gradle/wrapper/
COPY build.gradle settings.gradle gradle/ ./ 

# 소스 코드 복사
COPY src ./src

# Gradle 의존성 다운로드 및 빌드
RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:17-jdk-alpine

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]



