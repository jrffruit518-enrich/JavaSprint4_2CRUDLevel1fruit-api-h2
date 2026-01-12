# =========================
# 构建阶段（编译 Jar）
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# 先拷贝 pom.xml，利用 Docker 缓存
COPY pom.xml .
RUN mvn dependency:go-offline

# 再拷贝源码并打包
COPY src ./src
RUN mvn clean package -DskipTests


# =========================
# 运行阶段（最小化镜像）
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# 从构建阶段复制 jar
COPY --from=build /app/target/JavaSprint4_2CRUDLevel1fruit-api-h2-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot 默认端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]