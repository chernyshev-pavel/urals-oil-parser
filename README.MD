# Urals Oil Parser

Приложение для предоставления в виде REST API данных с ценами нефти марки "Юралс".

[Документация](https://github.com/chernyshev-pavel/urals-oil-parser/wiki/%D0%94%D0%BE%D0%BA%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D1%86%D0%B8%D1%8F-API)

## Сборка

### Клонирование репозитория

`git clone https://github.com/chernyshev-pavel/urals-oil-parser` <br>
`cd urals-oil-parser`

#### Для Windows

`gradlew.bat build` <br>

#### Для Linux

`chmod +x gradlew && ./gradlew build`<br>

При успешной сборке Jar файл с зависимостями будет лежать в `build\libs`

## Docker

### Создание образа

`sudo docker build --tag urals-oil-parser:1.0`

### Запуск контейнера

`sudo docker run --publish 8080:8080 urals-oil-parser`

