<table><tr><td><a href="https://github.com/JahnelGroup/journey-through-spring">Table of Contents</a></td></tr></table>

Understanding the “Magic” of Spring
======
You’ll often hear people refer to Spring as being complicated or difficult to learn. Spring is referred to as “magic” because its abilities at times seem impossible - until you look behind the curtain and understand what’s going on! Here are a few of the major features that Spring takes advantage of for it’s so-called magic.

## Auto-configuration: Classloader and Resource Management
Spring Boot introduced the concept of [Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html) which greatly reduced the learning curve of Spring. It leverages existing Java technology such as the Classloader and Resource Bundler to perform analysis on “what is present in the application” and make decisions based on it. For example if you have a MySQL Driver in your application then you’ll probably be connecting to a MySQL database so the auto-configuration will set that up for you. If you have Tomcat dependencies then you’ll probably be starting up a Tomcat container so the auto-configuration will do that for you. 

Auto-configuration is not just restricted to Spring developers, it is available for everyone to create their own Auto-configuration libraries to make their own “magic” happen automatically. 

### Classloader
A Java Classloader is the essential component that literally loads your classes into the JVM. Using this concept Spring can take advantage of knowing what’s present in your program (“on the classpath”) and make intelligent decisions about how and what to run. 

* **Read:** [**wikipedia.org:** Java Classpath](https://en.wikipedia.org/wiki/Classpath_(Java))
* **Read:** [**wikipedia.org:** Java Classloader](https://en.wikipedia.org/wiki/Java_Classloader)

### Resources
Spring will scan the resources (non-class files) on the classpath and make decisions on things. Two good examples of this are detecting the presence of properties and automatically loading them into your program, or detecting SQL files and automatically seeding your database for you. Features like this are constanting being added to make the development experience more efficient.

* **Read:** [**spring.io:** Resources](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/resources.html#resources)

## Reflection and Annotations
Spring relies heavily on Reflection to do almost everything. 

* **Read:** [**journaldev.com:** Java Reflection Example Tutorial](https://www.journaldev.com/1789/java-reflection-example-tutorial)
* **Read:** [**baeldung.com:** Call Methods at Runtime Using Java Reflection](http://www.baeldung.com/java-method-reflection)

[Annotations](https://en.wikipedia.org/wiki/Java_annotation) have evolved to be the standard way we provide “advice” to our Java programs. Spring uses annotations in place of external configuration files to drive the “wiring” of components. 

* **Read:** [**oracle.com:** Lesson: Annotations](https://docs.oracle.com/javase/tutorial/java/annotations/)
* **Read:** [**springframework.guru:** Spring Framework Annotations](https://springframework.guru/spring-framework-annotations/)

## Proxies
A [Proxy](https://en.wikipedia.org/wiki/Proxy) is the modeling of a relationship where one component is configured to act on-behalf-of another. For any given action the proxy will make a decision on what to do.

* Delegate the request as-is to the target
* Augment the request before delegating to the target
* Fulfill the request entirely by itself
* Ignore the request

You will learn how Spring manages your code as components called Beans (or “Managed Beans”). These Beans act as a Proxy to your code allowing Spring to make decisions on every action taken (i.e., function calls, auditing, transaction boundaries, error handling).

Spring Beans and the Proxy role they play in your application is the center stage in the Spring “magic show”.

> Side note that Spring won't *always* create a Proxy for your objects. It is intelligent enough to understand when it needs to and when it doesn't. If you're not doing anything interesting with the object anywhere in your application then it will not bother creating the proxy.

* **Watch:** [**youtube.com:** Understanding Spring Bean Factory](https://www.youtube.com/watch?v=xlWwMSu5I70)
* **Read:** [**spring.io:** Understanding Proxy Usage In Spring](https://spring.io/blog/2012/05/23/transactions-caching-and-aop-understanding-proxy-usage-in-spring)
* **Read:** [**spring.io:** Debunking Myths Proxies Impact Performance](https://spring.io/blog/2007/07/19/debunking-myths-proxies-impact-performance/)
