# ExampleBuild
При создании проекта можете удалить build.gradle в
изначальной папке, но можете и не удалять. Он всё равно
добавлен в .gitignore \
При изменении папок джавадоков и джакоко, нужно изменять 
oop.yml
Создание проекта такое же, как и обычное. Вначале git clown \
Дальше достаёте файлы, удаляете её, создаёте проект с такой
же папкой. 

Если у вас уже есть проект, и он схож с данным, то только
добавьте .github или изменённый oop.yml

# Инструкции для подключения Workflow

## Workflow файл

Для подключения workflow необходимо скопировать директорию `.github` в корень вашего репозитория (Можно запушить сразу в `master`). \
Добавьте .gitignore файл из примера. Можете удалить `build.gradle` из общей папки \
Теперь после каждого пуша в pull request (пуш в ветку на которой открыт pull request) будет запускать проверки, которые включают:
- Сборка Gradle проекта
- Запуск тестов
- Проверка покрытия кода тестами (должно быть больше 80%, отчёт будет прикплеплён как комментарий в pull request)
- Генерация javadoc и публикация их в ветку `gh-pages`
- Проверка кода на соответствие Google Java Style (замечания будут отображаться во вкладке `Files changed`

<b>
!!ВАЖНО!! </b>
при открытии pull request его имя должно совпадать с именем папки в которой находится код вашей лабораторной, например `Task_1_1_1` для лабораторной в первом семестре, из первого раздела номер один.


## Настройки репозитория

Для того, чтобы у вас был доступ к опубликованной документации лабораторных, необходимо зайти в `Settings > Pages` и в секции `Build and deployment` выбрать следующие параметры  
![image](https://user-images.githubusercontent.com/34095512/188311837-7168faff-b67b-4a58-afeb-1ba15552f658.png)


После этого вы сможете открывать вашу документацию по адресу `https://<Github username>.github.io/OOP/<Lab name>/`


Добавьте преподавателя в коллабораторы `settings->collaborators`

## Настройка `build.gradle`
Можете скачать/скопировать build.gradle с данного репозитория или самим настроить:
Для составления отчётов по покрытию тестами вашего кода, необходимо подключить в ваш gradle скрипт плагин `jacoco`. Для этого:
- Добавьте строчку `id 'jacoco'` в плагины
```Groovy
plugins {
    id 'java'
    id 'jacoco'
}
```
- Базовые `dependencies` для тестов
```Groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testImplementation 'org.junit.jupiter:junit-jupiter-params:5.8.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

- Измените задачу `jacocoTestReport`, чтобы она генерировала отчёт в формате `.xml`, а не только `.html` (достаточно скопировать код в конец вашего `build.gradle`)
- Чтобы не участвовали в проверке другие классы (например `Main.class`), изменит exclude
```Groovy
jacocoTestReport {
    dependsOn test // tests are required to run before generating the report
    reports {
        xml.required = true
        //html.outputLocation = layout.buildDirectory.dir('jacocoHtml') Доп опция, чтобы у себя можно было открыть html
    }
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.collect {
            fileTree(dir: it, exclude: [
                    "Main.class", //"<package>.Main.class" Указывайте полное имея пакета с точками
            ])
        }))
    }
}
```