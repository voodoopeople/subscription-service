# ---- STAGE 1: Собираем проект Gradle-образом ----
FROM gradle:7.5-jdk17 AS builder
WORKDIR /app

# Копируем весь контекст проекта (src, build.gradle.kts, settings.gradle.kts и т.д.)
COPY . .

# Собираем bootJar, пропуская тесты
RUN gradle clean bootJar --no-daemon -x test

# ---- STAGE 2: Минимальный runtime с JRE ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# копируем в корень контейнера как /app.jar
COPY --from=builder /app/build/libs/*.jar /app.jar

# Точка входа
ENTRYPOINT ["java","-jar","/app.jar"]
