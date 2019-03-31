# logDemo

Flag alerts to long-running events   

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


And repeat

```
build/reports/tests/test/index.html
```


## Built With

* [SpringBoot](http://www.dropwizard.io/1.0.2/docs/) - Dependency Injection & JPA & Testing frameworks (JUnit, AssertJ)
* [Gradle](https://maven.apache.org/) - Dependency Management
* [Jackson](https://github.com/FasterXML/jackson-databind) - Data binding
* [Logback](https://logback.qos.ch/) - Logging 
* [HSQLDB](http://hsqldb.org/) - Database

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
