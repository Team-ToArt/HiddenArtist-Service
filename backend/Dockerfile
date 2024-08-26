# Stage 1: Build Application
FROM eclipse-temurin:17-jdk-alpine AS build

# Set working Directory
WORKDIR /app

# Copy gradle wrapper and source code
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Make gradlew executable and build application
# dos 텍스트파일을 unix 형식으로 변환
RUN dos2unix ./gradlew
RUN chmod +x ./gradlew
RUN ./gradlew clean build --info
# 현재 문제점: Springboot Docker 빌드시 gradle-node 관련 에러 발생함.

# Stage 2: Create the final Image
FROM eclipse-temurin:17-jdk-alpine

# Copy Jar file from the builder stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose Application Port
EXPOSE 8080

# Run Application
ENTRYPOINT ["java","-jar","/app.jar"]