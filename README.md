
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
https://docs.google.com/spreadsheets/d/1YQtn9SlXpid_3mdb-ljrFWvucT7zg3RhqUgDLJdb3ag/edit?gid=0#gid=0

сделать для двух статистик код

Задание на лабу №2

1. В один из методов, использовавшийся для сбора статистики, добавить возможность задать задержку, имитирующую задержку получения результата, например из базы данных. К примеру, был метод getName(), в который нужно добавить параметр getName(long delay)

2. Заменить последовательный стрим, собирающий статистику из лабораторной №1, на параллельный. Поменять итоговую коллекцию, где собирается результат, на соответствующую потокобезопасную. Измерить производительность для разного количества элементов с дополнительной задержкой и без задержки. Для случаев с задержкой и без найти количество элементов, при котором сбор статистики последовательным и параллельным стримами дают одинаковую скорость выполнения.

3. Оптимизировать параллельный сбор статистики, реализовав собственный Spliterator. Измерить производительность своего варианта.

4. Измерения производительности выполнять с помощью фрейворка JMH.


RxJava значительно быстрее — в 7–9 раз!


Результаты

Benchmark                                          (delayMicros)  (userCount)  Mode  Cnt     Score     Error  Units
ActiveUsers.ActiveUsersBenchmark.parallelStreams              50          500  avgt    3   735,519 ?   65,605  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams              50         2000  avgt    3  2864,878 ? 2139,915  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams             500          500  avgt    3   735,173 ?   45,062  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams             500         2000  avgt    3  2955,892 ?  711,096  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams            1000          500  avgt    3   729,294 ?   78,902  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams            1000         2000  avgt    3  2954,942 ?  183,195  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable             50          500  avgt    3    87,408 ?   73,136  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable             50         2000  avgt    3   309,124 ?  136,464  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable            500          500  avgt    3    94,438 ?    2,662  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable            500         2000  avgt    3   331,029 ?   11,738  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable           1000          500  avgt    3    93,990 ?    6,614  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable           1000         2000  avgt    3   329,552 ?   16,045  ms/op
ReactiveBenchmark.countActiveWithFlowable                    N/A       100000  avgt    3  2321,593 ?  723,540  ms/op
ReactiveBenchmark.countActiveWithFlowable                    N/A       200000  avgt    3  4599,678 ? 4141,535  ms/op