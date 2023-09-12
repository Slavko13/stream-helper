Для запуска необходим docker

Собрать проект - mvn clean install -DskipTests

Сделать билд Dockerfile (точка тут не просто так если что)- docker build -t my-app .

Выполнить запуск докер образов с помощью файла для тест - docker-compose -f docker-compose.test.yml up -d

Используемые технологии

Spring boot
Java 17
Swagger 2
