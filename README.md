# Hulk Store API
This API allows managing all entities that are related to the project.

## Getting Started
To start you need install Java8 on your local machine.

## Development time
The project use [Gradle](https://gradle.org/) as a manager and to automate 
tasks. Use `build` gradle task to download local dependencies and compile 
the project and use `clean` to clean it.

For clean and compile the project:
```
./gradlew clean build
```

### Running server
This project uses gretty to mount the API and be able to make the 
corresponding requests locally.
```
./gradlew appRun
```
After the server is running, you can access the project through the browser 
in the next url (http://localhost:8080/api.hulkstore).

### Running the tests
The tests in this project are make with JUnit. You need use the 
following maven task:
```
./gradlew test
```

## Deployment
Just build the project you can get the packaged file (*.war) in the folder 
`/build/libs`, with the name of the project and the corresponding version.

But if in some case you are not able to run the following command and 
generate the file.
```
./gradlew war
```

All development rights are reserved by Gustavo Pacheco.
