---
title:  "Properties, Profiles and Resources"
permalink: /core/core-props
---

<i class="fas fa-book-reader"></i> Spring Framework: [Environment Abstraction](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-environment){:target="_blank"} and [Resources](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#resources){:target="_blank"}

You’ll likely need to change the behavior of your application based on a number of things. Spring has a lot features around this called Externalized Configuration - the practice of placing configuration in property files as opposed to hard-coded in your program. Spring will respect a very specific hierarchical order of properties such that you can override properties when required.  

* **Read:** [**spring.io:** Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html){:target="_blank"}
* **Read:** [**spring.io:** Properties and Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html){:target="_blank"}
* **Read:** [**baeldung.com:** Properties with Spring and Spring Boot](http://www.baeldung.com/properties-with-spring){:target="_blank"}
* **Read:** [**spring.io:** Appendix A. Common application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html){:target="_blank"}

Property files are typically placed in **src/main/resources** as either *.properties* or *.yml* files. **application.properties** and **application.yml** are specially named properties that Spring will look for to load automatically when your application starts. There are profile-specific variants of these property files as well and are described below. 

## Profiles
Spring has the concept of a Profile which is basically a special type of property that you can use to *tag* your components to a specific environment. Let’s say you have a service that should send an EMail out when certain events happen in your system. This may only be relevant in an environment like UAT and PROD but not in DEV. You could define two types of MailService, one to actually send the EMail out and the other to just print it to the console.

* **Read:** [**spring.io:** Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html){:target="_blank"}
* **Read:** [**baeldung.com:** Spring Profiles](http://www.baeldung.com/spring-profiles){:target="_blank"}

```kotlin
/** 
  * Only one of the two Beans defined in this class will be loaded in any given environment. 
  */ 
@Configuration
class MyConfig {

  // Use the real EMailService in UAT and PROD
  @Bean
  @Profile(“UAT”, “PROD”)
  fun emailService(): MailService = EMailService()

  // Use the console based service for DEV
  @Bean
  @Profile(“DEV”)
  fun consoleMailService(): MailService = ConsoleMailService()

}
```

There are several ways to make a Profile "active", one such way is to pass the **spring.profiles.active** JVM argument.

```bash
$ java -jar -Dspring.profiles.active=PROD app.jar
```

Profiles work very nicely with the properties mentioned earlier. Spring will load properties based on a naming scheme with the Profile name in the format application-{profile}.properties (i..e, application-PROD.properties). Profile specific properties will override the default properties. 

A common example of this is externalizing database connection property for each envirionment.

* src/main/resources/application.properties
  * spring.datasource.url=jdbc:mysql://localhost:3306/mydb
* src/main/resources/application-DEV.properties
  * spring.datasource.url=jdbc:mysql://dev-database.app.com:3306/mydb
* src/main/resources/application-PROD.properties
  * spring.datasource.url=jdbc:mysql://prod-database.app.com:3306/mydb
  
## Resources
Resources are non source-code related files required by your application. The Properties described above are considered resources. SQL Files, CSV, XLS images, bundled HTML/CSS/JS and others would also be considered resources for a Spring application. Spring will make resources from your classpath available to you, and it will automatically make **src/main/resources** part of your classpath. 

### Some Important Specially Named Resources
All of these are located in src/main/resources.

| File                          | Description          |
| ----------------------------- | -------------------- |
| application.\[properties\|yml] | Described in the above Properties section. |
| schema.sql                    | [Initialize a Database](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc){:target="_blank"} with simple DDL script. |
| data.sql                      | [Initialize a Database](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc){:target="_blank"} with simple seed script. |
| db/migrations/V*.sql          | [Execute Flyway Database Migrations on Startup](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup){:target="_blank"} |
| banner.txt                    | [Customize the Banner](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-banner){:target="_blank"} that displays when Spring starts up. | 

### Access Resources in your Code
Spring can inject Resources for you quite easily. Here is an example of printing a text file line-by-line.

**src/main/resources/printMe.txt**
```text
Line 1
Line 2
etc...
```

This short piece of code is instructing Spring to inject a reference to the classpath resource and then using some neat Kotlin extension functions to iterate over it. The key point here is the @Value injection of a Resource to get a reference to the File. 

**src/main/kotlin/DemoApplication.kt**
```kotlin
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.Resource
import javax.annotation.PostConstruct

@SpringBootApplication
class DemoApplication{

    /**
      * Spring is doing a couple of things here to make this magic happen. It detects
      * the injection type to be a Resource so it knows its a file. The @Value annotation
      * is a URI pointing to a file named printMe.txt located in the root of the classpath.
      * You can then get a File reference from the Resource.      
      */
    @Value("\${classpath:printMe.txt}") var printMe: Resource? = null

    // Prints the File line by line
    @PostConstruct fun init() = printMe!!.file.readLines().forEach { println(it) }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
```