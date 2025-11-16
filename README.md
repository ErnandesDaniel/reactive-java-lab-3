
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

Benchmark                                                   (delayMicros)  (userCount)  Mode  Cnt    Score    Error  Units
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      0         3000  avgt    3    0,017 ?  0,042  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      0         5000  avgt    3    0,021 ?  0,045  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      0        50000  avgt    3    0,090 ?  0,014  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      0       250000  avgt    3    1,342 ?  0,095  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2         3000  avgt    3    0,636 ?  0,045  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2         5000  avgt    3    1,033 ?  0,055  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2        50000  avgt    3    9,951 ?  0,368  ms/op
ActiveUsers.ActiveUsersBenchmark.customSpliterator                      2       250000  avgt    3   51,599 ? 25,659  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              0         3000  avgt    3    0,009 ?  0,001  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              0         5000  avgt    3    0,026 ?  0,003  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              0        50000  avgt    3    0,294 ?  0,052  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              0       250000  avgt    3    6,046 ?  0,278  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2         3000  avgt    3    6,033 ?  0,023  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2         5000  avgt    3   10,066 ?  0,080  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2        50000  avgt    3  104,639 ?  5,485  ms/op
ActiveUsers.ActiveUsersBenchmark.oneStream                              2       250000  avgt    3  533,896 ?  1,260  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        0         3000  avgt    3    0,018 ?  0,049  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        0         5000  avgt    3    0,020 ?  0,053  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        0        50000  avgt    3    0,086 ?  0,005  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        0       250000  avgt    3    1,160 ?  0,057  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2         3000  avgt    3    0,635 ?  0,047  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2         5000  avgt    3    1,033 ?  0,047  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2        50000  avgt    3    9,972 ?  0,451  ms/op
ActiveUsers.ActiveUsersBenchmark.parallelStreams                        2       250000  avgt    3   51,628 ? 43,494  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              0         3000  avgt    3    0,049 ?  0,014  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              0         5000  avgt    3    0,056 ?  0,001  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              0        50000  avgt    3    0,187 ?  0,026  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              0       250000  avgt    3    2,654 ?  0,458  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2         3000  avgt    3    0,646 ?  0,045  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2         5000  avgt    3    1,038 ?  0,052  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2        50000  avgt    3   10,067 ?  2,278  ms/op
ActiveUsersList.ActiveUsersListBenchmark.customSpliterator              2       250000  avgt    3   53,140 ? 61,718  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      0         3000  avgt    3    0,028 ?  0,013  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      0         5000  avgt    3    0,060 ?  0,014  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      0        50000  avgt    3    0,758 ?  0,564  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      0       250000  avgt    3   14,080 ? 24,129  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2         3000  avgt    3    6,053 ?  0,172  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2         5000  avgt    3   10,128 ?  0,251  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2        50000  avgt    3  105,430 ?  6,229  ms/op
ActiveUsersList.ActiveUsersListBenchmark.oneStream                      2       250000  avgt    3  537,430 ?  4,351  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                0         3000  avgt    3    0,055 ?  0,014  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                0         5000  avgt    3    0,061 ?  0,056  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                0        50000  avgt    3    0,180 ?  0,036  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                0       250000  avgt    3    2,640 ?  0,106  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2         3000  avgt    3    0,672 ?  0,321  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2         5000  avgt    3    1,074 ?  0,433  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2        50000  avgt    3   10,506 ?  6,949  ms/op
ActiveUsersList.ActiveUsersListBenchmark.parallelStreams                2       250000  avgt    3   50,474 ?  0,503  ms/op



