# phone-numbers-predictor-service
0. Скопируйте данный репозиторий на вашу локальную машину
1. Для запуска тестового сервера следует убедиться в том, что в системе установлен Docker. В случае, если Docker не найден, слудет установить его на целевую машину
2. Собрать Docker образ: docker build -t rt-server .
3. Создать и запустить контейнер: docker run -p 8080:8080 -d --name rt rt-server
4. Убедившись в работе, можно удалить контейнер: docker rm --force rt

# Примеры запросов
http://localhost:8080/rest/code?counties=R

http://localhost:8080/rest/code?counties=Ru
