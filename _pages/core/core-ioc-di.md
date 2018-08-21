---
title:  "Inversion of Control and Dependency Injection"
permalink: /core/core-ioc-di
---

<i class="fas fa-book-reader"></i> Spring Framework: [Container overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-basics){:target="_blank"}

The two main concepts fundamental to Spring are [Inversion of Control](https://en.wikipedia.org/wiki/Inversion_of_control){:target="_blank"} and [Dependency Injection](https://en.wikipedia.org/wiki/Dependency_injection){:target="_blank"}. Understanding the value of these concepts is critical to your success. 

> *IoC is also known as dependency injection (DI). It is a process whereby objects define their dependencies, that is, the other objects they work with, only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse, hence the name Inversion of Control (IoC), of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes, or a mechanism such as the Service Locator pattern. - [Spring Docs](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-introduction){:target="_blank"}*

* **Read:** [**baeldung.com:** Intro to Inversion of Control and Dependency Injection with Spring](http://www.baeldung.com/inversion-control-and-dependency-injection-in-spring){:target="_blank"}

## Inversion of Control (IoC) Container / Application Context
At the center of a Spring application is the [IoC Container](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans){:target="_blank"} which is represented by the [ApplicationContext](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContext.html){:target="_blank"} Class. It is the orchestrator of your code and manager of your Objects. It is essentially the glue to your entire application and knows everything. You start the Application Context in your main function.

* **Read:** [**spring.io:** The “main” Method](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-main-method){:target="_blank"}

```kotlin
package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args) // starts the IoC Container (i.e., ApplicationContext)
}
```

When you start the IoC Container it will scan your application from the current package down (i.e, com.example.demo\*.\*) to create beans, provide advice, establish database connection pools, any many more things. Your main function should do nothing other than start the Spring ApplicationContext - everything from this point is now driven by Spring constructs. 

* **Read:** [**spring.io:** Additional Capabilities of the ApplicationContext](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-introduction){:target="_blank"}

## Dependency Injection
Dependency Injection is a technique used to achieve Inversion of Control and sounds more sophisticated than it really is. If *Object A* depends on *Object B* then a reference will be provided ("injected") from an external source (i.e., the ApplicationContext). If *Object A* creates *Object B* on it's own then it is not Dependency Injection. 

In Spring we achieve this by creating [Beans](./core-beans) and establishing their relationship in a few different ways. The two most common are through **Constructor Injection** or **Property Injection** both discussed in the next section.

* **Watch:** [**youtube.com:** Understanding Spring Bean Factory](https://www.youtube.com/watch?v=xlWwMSu5I70){:target="_blank"}
* **Watch:** [**youtube.com:** Understanding Dependency Injection](https://www.youtube.com/watch?v=GB8k2-Egfv0&t=493s){:target="_blank"}
