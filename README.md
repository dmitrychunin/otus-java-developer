## Домашние задания курса "Разработчик Java"

<details><summary>hw01 Проект maven с модульной структурой</summary>
<p>
1) Создать аккаунт на github.com (если еще нет)<br/>
2) Создать репозиторий для домашних работ<br/>
3) Сделать checkout репозитория на свой компьютер<br/>
4) Создайте локальный бранч hw01-maven<br/>
5) Создать проект maven<br/>
6) В проект добавьте последнюю версию зависимости<br/>
<groupId>com.google.guava</groupId><br/>
<artifactId>guava</artifactId><br/>
7) Создайте модуль hw01-maven<br/>
8) В модуле сделайте класс HelloOtus<br/>
9) В этом классе сделайте вызов какого-нибудь метода из guava<br/>
10) Добавьте нужный плагин maven и соберите "толстый-jar"<br/>
11) Убедитесь, что "толстый-jar" запускается.<br/>
12) Сделайте pull-request в gitHub<br/>
13) Ссылку на PR отправьте на проверку.<br/>
</p>
</details>
<details><summary>hw02 DIY ArrayList</summary>
<p>
Написать свою реализацию ArrayList на основе массива.
class DIYarrayList<T> implements List<T>{...}

Проверить, что на ней работают методы из java.util.Collections:<br/>
```java
Collections.addAll(Collection<? super T> c, T... elements)
Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
```

1) Проверяйте на коллекциях с 20 и больше элементами.
2) DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.
</p>
</details>
<details><summary>hw03 Свой тестовый фреймворк.</summary>
<p>
Написать свой тестовый фреймворк.

Поддержать свои аннотации @Test, @Before, @After.

Запускать вызовом статического метода с именем класса с тестами.

Т.е. надо сделать:
1) создать три аннотации - @Test, @Before, @After.
2) Создать класс-тест, в котором будут методы, отмеченные аннотациями.
3) Создать "запускалку теста". На вход она должна получать имя класса с тестами.
4) "Запускалка" должна в классе-тесте найти и запустить методы, отмеченные аннотациями.
5) Алгоримт запуска должен быть такой:
метод Before
метод Test
метод After
для каждой такой "тройки" надо создать СВОЙ объект класса-теста.
6) Исключение в одном тесте не должно прерывать весь процесс тестирования.
</p>
</details>
<details><summary>hw04 Автомагическое логирование.</summary>
<p>
Разработайте такой функционал:
метод класса можно пометить самодельной аннотацией @Log, например, так:

```java
class TestLogging {
  @Log
  public void calculation(int param) {};
}
```
При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.

```java
class Demo {
  public void action() {
    new TestLogging().calculation(6);
  }
}
```
В консоле дожно быть:
executed method: calculation, param: 6

Обратите внимание: явного вызова логирования быть не должно.
</p>
</details>
<details><summary>hw05 Сравнение разных сборщиков мусора</summary>
<p>
Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа
(young, old) и время которое ушло на сборки в минуту.

Добиться OutOfMemory в этом приложении через медленное подтекание по памяти
(например добавлять элементы в List и удалять только половину).

Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало
с OOM примерно через 5 минут после начала работы.

Собрать статистику (количество сборок, время на сборки) по разным GC.

!!! Сделать выводы !!!
ЭТО САМАЯ ВАЖНАЯ ЧАСТЬ РАБОТЫ:
Какой gc лучше и почему?
</p>
</details>
<details><summary>hw06 Эмулятор банкомата</summary>
<p>
Написать эмулятор АТМ (банкомата).

Объект класса АТМ должен уметь:
- принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
- выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
Это задание не на алгоритмы, а на проектирование.
Поэтому оптимизировать выдачу не надо.
- выдавать сумму остатка денежных средств
</p>
</details>
<details><summary>hw06 Департамент ATM</summary>
<p>
Написать приложение ATM Департамент:<br/>
1) Департамент может содержать несколько ATM.<br/>
2) Департамент может собирать сумму остатков со всех ATM.<br/>
3) Департамент может инициировать событие – восстановить состояние всех
ATM до начального (начальные состояния у разных ATM могут быть
разными).<br/>
Это тренировочное задание на применение паттернов.<br/>
Попробуйте использовать как можно больше.
</p>
</details>
<details><summary>hw08 Cвой json object writer</summary>
<p>
Напишите свой json object writer (object to JSON string) аналогичный gson на основе javax.json.

Поддержите:
- массивы объектов и примитивных типов
- коллекции из стандартный библиотеки.
</p>
</details>
<details><summary>hw09 Самодельный ORM</summary>
<p>
Работа должна использовать базу данных H2.
Создайте в базе таблицу User с полями:

• id bigint(20) NOT NULL auto_increment
• name varchar(255)
• age int(3)

Создайте свою аннотацию @Id

Создайте класс User (с полями, которые соответствуют таблице, поле id отметьте аннотацией).

Напишите JdbcTemplate, который умеет работать с классами, в которых есть поле с аннотацией @Id.
JdbcTemplate должен сохранять объект в базу и читать объект из базы.
Имя таблицы должно соответствовать имени класса, а поля класса - это колонки в таблице.

Методы JdbcTemplate'а:

```java
void create(T objectData);
void update(T objectData);
void createOrUpdate(T objectData); // опционально.
<T> T load(long id, Class<T> clazz);
```

Проверьте его работу на классе User.

Метод createOrUpdate - необязательный.
Он должен "проверять" наличие объекта в таблице и создавать новый или обновлять.

Создайте еще одну таблицу Account:<br/>
• no bigint(20) NOT NULL auto_increment<br/>
• type varchar(255)<br/>
• rest number<br/>

Создайте для этой таблицы класс Account и проверьте работу JdbcTemplate на этом классе.

Еще одна опция (по желанию):
Реализовать абстракцию DBService для объектов User и Account.

И еще одна опция (по желанию для супер-мега крутых бизонов):
прикрутите этот "jdbc-фреймворк" к департаменту ATM.
</p>
</details>
<details><summary>hw10 Использование Hibernate</summary>
<p>
Работа должна использовать базу данных H2.

Возьмите за основу предыдущее ДЗ (Самодельный ORM)
и реализуйте функционал сохранения и чтения объекта User через Hibernate.
(Рефлейсия больше не нужна)
Конфигурация Hibernate должна быть вынесена в файл.

Добавьте в User поля:
адрес (OneToOne)

```java
class AddressDataSet {
private String street;
}
```
и телефон (OneToMany)
```java
class PhoneDataSet {
private String number;
}
```

Разметьте классы таким образом, чтобы при сохранении/чтении объека User каскадно сохранялись/читались вложенные объекты.
</p>
</details>
<details><summary>hw11 Свой cache engine</summary>
<p>
Напишите свой cache engine с soft references.
Добавьте кэширование в DBService из задания про Hibernate ORM
</p>
</details>
<details><summary>hw12 Веб сервер</summary>
<p>
Встроить веб сервер в приложение из ДЗ про Hibernate ORM.
Сделать админскую страницу, на которой админ должен авторизоваться.
На странице должны быть доступны следующие функции:
- создать пользователя
- получить список пользователей

При желании, пользователей можно сохранять в MongoDB.
</p>
</details>
<details><summary>hw13 Приложение с IoC контейнером</summary>
<p>
Собрать war для приложения из предыдущего ДЗ.
Создавать кэш и DBService как Spring beans, передавать (inject) их в сервлеты.
Запустить веб приложение во внешнем веб сервере.
</p>
</details><details><summary>hw14 Последовательность чисел</summary>
<p>
Два потока печатают числа от 1 до 10, потом от 10 до 1.
Надо сделать так, чтобы числа чередовались, т.е. получился такой вывод:
Поток 1:1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3 4....
Поток 2: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
</p>
</details>
<details><summary>hw15 MessageSystem</summary>
<p>
Добавить систему обмена сообщениями в ДЗ про веб сервер с IoC контейнером.
Пересылать сообщения из вебсокета в DBService и обратно.
Организовать структуру пакетов без циклических зависимостей.
</p>
</details>
<details><summary>hw16 MessageServer</summary>
<p>
Cервер из предыдущего ДЗ про MessageSystem разделить на три приложения:<br/>
• MessageServer<br/>
• Frontend<br/>
• DBServer<br/>
Запускать Frontend и DBServer из MessageServer.<br/>
Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами.<br/>
Пересылать сообщения с Frontend на DBService через MessageServer.<br/>

Запустить приложение с двумя серверами фронтенд и двумя серверами баз данных на разных портах.<br/>
Если у вас запуск веб приложения в контейнере, то MessageServer может копировать root.war в контейнеры при старте
</p>
</details>
