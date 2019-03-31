# logDemo

Add a alert flag to long-running events.


If the log file to read is large (gigabytes), then the events will be partitioned to slices, each slice will be 
handled by a thread, thus improve the efficiency.

By default the slice size is 10000 events.   

## Getting Started

From the root directory (logDemo), run the following commands:
### Run

```
./gradlew build

java -jar build/libs/logDemo-0.0.1-SNAPSHOT.jar log.txt

```

### Running the tests
```
./gradlew test
```

### Test Report
```
{path_to_root_dir}/build/reports/tests/test/index.html
```

### Access to DB
```
java -cp hsqldb-2.4.1/hsqldb/lib/hsqldb.jar  org.hsqldb.util.DatabaseManagerSwing

type: hsql database engine standalone
url: jdbc:hsqldb:file:testdb
user: SA
password:
```

## Built With

* [SpringBoot](http://www.dropwizard.io/1.0.2/docs/) - Dependency Injection & JPA & Testing frameworks (JUnit, AssertJ)
* [Gradle](https://maven.apache.org/) - Dependency Management
* [Jackson](https://github.com/FasterXML/jackson-databind) - Data binding
* [Logback](https://logback.qos.ch/) - Logging 
* [HSQLDB](http://hsqldb.org/) - Database