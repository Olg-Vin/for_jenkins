# Используем официальный образ JDK для сборки приложения
FROM maven:3.8.4-openjdk-17-slim AS builder

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файл pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем весь код проекта и собираем приложение
COPY src /app/src
RUN mvn clean package -DskipTests

# Создаем финальный образ для запуска приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл из предыдущего этапа
COPY --from=builder /app/target/for_jenkins-1.0-SNAPSHOT.jar app.jar

# Открываем порт для приложения (если приложение слушает на порту 8080, например)
EXPOSE 8081

# Команда для запуска JAR-файла
CMD ["java", "-jar", "app.jar"]
