# Automated Test Framework with Java, RestAssured, Junit5 e Gradle

This is an example of an automated test framework for studying both programming and automated testing.

This is a course made by [Vinicius Pessoni]( https://www.youtube.com/c/pessonizando)

The objective of this work was to learn how to use the best tools to create our automated test
and be able to create our test framework in the best way possible.

### How will execute the tests -> Junit 5

### A way to check the results -> Junit5/Hamcrest

### Programming language -> Java

### Additional tools:

  ### Simplify requests -> restAssured

  ### Reports and dependencies -> gradle

### Requirements

Download and install [INTELIJ](https://www.jetbrains.com/idea/)

Download and install for latest JDK [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


### How to run this project using IntelliJ or Terminal
Clone or download this project to your computer.

Clone or download the [APIS Test Course Sample API](https://github.com/vinnypessoni/exemplo-API) and run it according to the API README.


### Running by IntelliJ

After importing this project into IntelliJ, navigate to the src/test/java folder and open the TestClient class.

A green button (a play) should appear next to the class name on line 11, just press it.

The tests will be compiled and the results will be displayed on the Intellij run screen.

### Running by Terminal

Another possibility is to run them through the terminal.

Navigate to the folder where you downloaded or cloned the files and use the commands:

    Linux/Mac
    
        ./gradlew clean test  
    
    Windows
    
        gradlew clean test 

### Reports

Test results are displayed on the screen of the Intellij or terminal.
 
In addition, we generate an .html report on each run.

This report is in the folder

    build -> reports -> tests -> test -> index.html
