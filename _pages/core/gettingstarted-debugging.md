---
title:  "Debugging"
permalink: /core/gettingstarted-debugging
---

You just wrote an awesome program, fired it up and ugh-o...it doesn't work like you expect! Writing code is *easy* figuring out what's wrong with broken code is *really hard*. You have a couple of options.

## Print Statements

Yeah that's right, the good old print statement. It's the worst way to debug a problem but it sure is a technique and we've all been there so why not cover it. Doing this has so many problems but the main reason I feel is that you're actually editing source-code for debugging and you'll likely end up just leaving it there to flood the logs in production. 

**Do not do this.**

```kotlin
println("condition = $condition")
if( condition ) {
  println("Yep, condition sure is true")
}else{
  println("No way, condition is false!")
}
```

## Logger

Instead of print statements you should opt for using a [Logger](https://en.wikipedia.org/wiki/Java_logging_framework){:target="_blank"}. It may seem the same but there are important differences.

* It provides a standard output which includes the origin class name where the output came from
* You an enable/disable a logger based on a log level
* You can change the format output of log, plain text, json, csv, etc
* You can change the destination, a file, a database, a queue, etc
* You can add meta information to a log file called a [Mapped Diagnostic Context (MDC)](http://www.baeldung.com/mdc-in-log4j-2-logback){:target="_blank"}

The logger world is full of *specifications* and *implementations* There are several logging frameworks available but here are the popular ones:

* [Apache Commons Logging](https://commons.apache.org/proper/commons-logging/){:target="_blank"} - Spring Framework Specification uses this specification.
* [Logback](https://logback.qos.ch/){:target="_blank"} - Logback is intended as a successor log4j
* [Log4j](https://logging.apache.org/log4j/2.x/){:target="_blank"} - Apache Log4j
* [SLF4J](https://www.slf4j.org){:target="_blank"} - Abstract Layer over many loggers, you use this with an implementation like Log4j or Logback.

<i class='fas fa-bookmark'></i> Read: [**loggly.com:** Java Logging Basics](https://www.loggly.com/ultimate-guide/java-logging-basics/){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [**spring.io:** Spring Boot Doc: How to Logging](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [**spring.io:** Spring Boot Doc: Boot Logging Features](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html){:target="_blank"}

### Logging Level

To change the logging level you need to set the associated property, either in application.properties, command line or some other way. The property has a special format:

> **logging.level**.fully.qualified.package.name.*OptionalClassname*=LEVEL

Here are a couple of examples.

```properties
# Change every logger under the org.* package to DEBUG
logging.level.org=DEBUG

# Change only the loggers under the package com.jahnelgroup to DEBUG
logging.level.com.jahnelgroup=DEBUG

# Change only the UserController logger to DEBUG
logging.level.com.jahnelgroup.user.UserController=DEBUG
```

### Using a Logger

Whatever implementation you choose you'll likely get a reference to a logger through a factory. Unless otherwise required you should code against the SLF4J abstract layer.

```kotlin
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserController(var userService: UserService) {

  // companion is a Kotlin way of saying static
  companion object {
    // You want to use the class name as the name of the logger
    val log: Logger = LoggerFactory.getLogger(UserController::class.java)
  }
  
  fun something(var req: Reqest) {

    // this will only print if trace level logging is on
    log.trace("Doing something with request $req")
  
    // you are not required to wrap your logging statements in a condition like this
    // but it will help the performance of your application under high load. By adding this 
    // condition you'll skip over the processing time needed to construct the String and pass
    // it the logger. The logger will ultimately check this boolean again but it's good not
    // spend time creating a String if it's just going to be ignore. 
    if(log.isDebugEnabled){
      log.debug("The request is for user ${userService.findById(req.userId).username}")
    }
  
  }
}
```

## Debugger

If bug a is too difficult to resolve with just logging statements then you'll need to resort to using [debugger](https://en.wikipedia.org/wiki/Debugger){:target="_blank"}. These tools will help you suspend the execution of your program and manually step through it just like it normally would while running in isolation. 

### Debugging in an IDE

Debugging in an IDE is very simple, instead of selecting to **Run** your application you choose **Debug**. Set some break-points and you're done. 

<i class='fas fa-bookmark'></i> Read: [**jetbrains.com:** IntelliJ: Debugging Your First Java Application](https://www.jetbrains.com/help/idea/debugging-your-first-java-application.html){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [**vogella.com:** Eclipse: Java Debugging](http://www.vogella.com/tutorials/EclipseDebugging/article.html){:target="_blank"}<br/>
<i class='fas fa-play'></i> Watch: [**youtube.com:** Debugging in IntelliJ IDEA 2016.1](https://www.youtube.com/watch?v=VdBsUv4lnm4){:target="_blank"}

### Remote Attaching to a JVM

Connecting to an external JVM outside the IDE takes a little more work. A remote debugger works by connecting to a special port exposed by the JVM. It's not available by default so you must enable it with JVM arguments when it starts. 

> Note that if your JVM is behind a firewall then that port must be open as well. You can get around a firewall with an [SSH Tunnel](https://blog.trackets.com/2014/05/17/ssh-tunnel-local-and-remote-port-forwarding-explained-with-examples.html) if you have ssh access.

You can pick any available port, IntelliJ defaults with 5005. Take notice of the Listening for transport output as its an indication to you that it's ready for remote debuggers.

```bash
$ java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar app.jar
Listening for transport dt_socket at address: 5005
```

<i class='fas fa-bookmark'></i> Read: [**jetbrains.com:** IntelliJ: Run/Debug Configuration: Remote Debug](https://www.jetbrains.com/help/idea/run-debug-configuration-remote-debug.html){:target="_blank"}
<i class='fas fa-bookmark'></i> Read: [**medium.com:** Eclipse: Remote Debugging for a Java](https://medium.com/@metamje/setting-up-remote-debugging-for-a-java-application-in-eclipse-with-heroku-exec-22d0722371c2){:target="_blank"}

## Difficulty with Spring Proxies

The hardest part about debugging a Spring Application are the Proxies. You'll sometimes find yourself jumping through a lot of Spring code just trying to find where it calls your code. If you find this happening then try to make an educated guess as to where your code is being hit and set a lot of break points to find it. You may resort to enabling a debug or trace logging level to get more visibility on what's happening.