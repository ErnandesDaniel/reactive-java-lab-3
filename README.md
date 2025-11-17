
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


Результаты

ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2          500  avgt    3      0,136 ?    0,027  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2         2000  avgt    3      0,451 ?    0,120  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2          500  avgt    3      1,023 ?    0,009  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2         2000  avgt    3      4,131 ?    1,105  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2          500  avgt    3      0,149 ?    0,154  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2         2000  avgt    3      0,497 ?    0,262  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                       2          500  avgt    3      1,498 ?    0,397  ms/op
ActiveUsers.ActiveUsersBenchmark.rxJavaObservable                       2         2000  avgt    3      5,983 ?    1,304  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2          500  avgt    3      0,153 ?    0,078  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2         2000  avgt    3      0,473 ?    0,089  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2          500  avgt    3      1,012 ?    0,008  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2         2000  avgt    3      4,068 ?    0,096  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2          500  avgt    3      0,163 ?    0,056  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2         2000  avgt    3      0,470 ?    0,084  ms/op
ActiveUsersList.ActiveUsersListBenchmark.rxJavaObservable               2          500  avgt    3      1,508 ?    0,108  ms/op
ActiveUsersList.ActiveUsersListBenchmark.rxJavaObservable               2         2000  avgt    3      5,838 ?    0,778  ms/op
ReactiveBenchmark.collectActiveWithFlowable                           N/A       100000  avgt    3   2381,485 ? 1634,320  ms/op
ReactiveBenchmark.collectActiveWithFlowable                           N/A       200000  avgt    3   5076,715 ? 3740,066  ms/op
ReactiveBenchmark.collectActiveWithFlowable                           N/A       500000  avgt    3  12118,439 ? 6111,471  ms/op
ReactiveBenchmark.countActiveWithFlowable                             N/A       100000  avgt    3   2423,726 ? 1329,660  ms/op
ReactiveBenchmark.countActiveWithFlowable                             N/A       200000  avgt    3   5051,482 ? 2208,974  ms/op
ReactiveBenchmark.countActiveWithFlowable                             N/A       500000  avgt    3  12174,116 ? 1354,632  ms/op

