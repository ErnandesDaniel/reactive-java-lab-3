
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

Benchmark                                                 (delayMicros)  (userCount)  Mode  Cnt     Score      Error  Units
ActiveUsers.ActiveUsersBenchmark.parallelStreams                     50          500  avgt    3   733,141 ?   86,749  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                     50         2000  avgt    3  2939,464 ?  133,498  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                    50          500  avgt    3    87,388 ?   39,946  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                    50         2000  avgt    3   310,941 ?   86,408  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams             50          500  avgt    3   734,991 ?   26,821  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams             50         2000  avgt    3  2942,077 ?   44,968  ms/op
ReactiveBenchmark.countActiveWithFlowable                           N/A       100000  avgt    3  2415,483 ? 1626,242  ms/op
ReactiveBenchmark.countActiveWithFlowable                           N/A       200000  avgt    3  4790,837 ? 3458,545  ms/op





