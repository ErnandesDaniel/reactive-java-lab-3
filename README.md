
Сборка проекта:
mvn clean package

Запуск проекта (Выделена дополнительная память, чтобы не возникало ошибок):
java -Xms2g -Xmx12g -jar target/benchmarks.jar


Чтобы читать результаты нужно смотреть на строки:

Iteration   1: 5010,091 ms/op

Одна операция (@Benchmark-метод) заняла в среднем 5010,091 миллисекунд. 

в конце блока вы увидите усреднённое значение:

Result "org.example.stats.ActiveUsers.ActiveUsersBenchmark.parallelCustom":
5010,1 ?(99.9%) 10,2 ms/op [Average]
(min, avg, max) = (5008, 5010, 5012)

число avg и есть итоговое время выполнения метода

Результаты:
https://docs.google.com/spreadsheets/d/1QcXzDxHk-fb4TClLMOCpr40QECQGfP-DaRdx_-A3gl0/edit?gid=0#gid=0

https://docs.google.com/document/d/1cEkBXx9-LRmE6ucJFA-4fDd4StCPBP7G4xDfM-p-ZTM/edit?tab=t.0


сделать для двух статистик код

Задание на лабу №2

1. В один из методов, использовавшийся для сбора статистики, добавить возможность задать задержку, имитирующую задержку получения результата, например из базы данных. К примеру, был метод getName(), в который нужно добавить параметр getName(long delay)

2. Заменить последовательный стрим, собирающий статистику из лабораторной №1, на параллельный. Поменять итоговую коллекцию, где собирается результат, на соответствующую потокобезопасную. Измерить производительность для разного количества элементов с дополнительной задержкой и без задержки. Для случаев с задержкой и без найти количество элементов, при котором сбор статистики последовательным и параллельным стримами дают одинаковую скорость выполнения.

3. Оптимизировать параллельный сбор статистики, реализовав собственный Spliterator. Измерить производительность своего варианта.

4. Измерения производительности выполнять с помощью фрейворка JMH.


RxJava значительно быстрее — в 7–9 раз!

Результаты

Benchmark                                                 (delayMicros)  (userCount)  Mode  Cnt     Score      Error  Units
ActiveUsers.ActiveUsersBenchmark.parallelStreams                     50          500  avgt    3   738,192 ?   42,093  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                     50         2000  avgt    3  2931,664 ?  176,322  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                    50          500  avgt    3    88,943 ?   43,073  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                    50         2000  avgt    3   310,479 ?  121,472  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams             50          500  avgt    3   733,481 ?   47,980  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams             50         2000  avgt    3  2942,108 ?  173,634  ms/op
ReactiveBenchmark.collectActiveWithFlowable                         N/A       100000  avgt    3  2499,650 ? 4730,296  ms/op
ReactiveBenchmark.collectActiveWithFlowable                         N/A       200000  avgt    3  4886,128 ?  252,220  ms/op
ReactiveBenchmark.countActiveWithFlowable                           N/A       100000  avgt    3  2650,729 ? 3389,061  ms/op
ReactiveBenchmark.countActiveWithFlowable                           N/A       200000  avgt    3  6046,212 ? 7543,503  ms/op


