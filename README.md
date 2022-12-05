# IApps task

## Requirements

For building and running the application you need:
Java 11, 
MySQL, 
Eclipse/STS/CMD/TERMINAL

## Running the application locally

- Changes your username and Password for mysql from application.properties file.
- Before starting application you must need to build the project using following command.

```shell
mvn clean install
```
Command to execute:-

```shell
java -jar target/IApps-0.0.1-SNAPSHOT.jar
```

or 

```shell
mvn spring-boot:run
```

For testing test cases:- In STS/Eclipse Run IAppsApplicationTests.java with JUnit Test or run "mvnw test"

API endpoints:

Post API:
URL:- http://localhost:8080/api/upload/paper
Key                paperXml 
xmlFile	           epaper-valid.xml(select xml file)	//Also added valid and invalid xml files for testing in resources folder.

Get API with filters: (All paramaters are non mandatory.)
Url:- http://localhost:8080/api/getPapers
params are:- 
1. fromDate, toDate in long milliseconds format
2. pageNo (starting with 0)
3. pageSize (no of records you want in result)
4. sortBy (specific column name on which you need to sort)
5. asc (true for ascending, false for descending approach on sortBy column)
6. search (keyword for searching)