# Используем официальный базовый образ для Java 17
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл из вашего проекта в контейнер
COPY target/streamers-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]
