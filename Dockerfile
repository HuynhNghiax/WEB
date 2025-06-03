# Stage 1: Build WAR bằng Maven
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Copy WAR vào Tomcat
FROM tomcat:10.1.33-jdk21
# Xóa app mặc định (nếu muốn sạch)
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy WAR build từ stage 1
COPY --from=builder /app/target/DoAnLTWeb-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
