---
title:  "Understanding the “Magic” of Spring"
permalink: /core/gettingstarted-magic
---

You’ll often hear people refer to Spring as being complicated or difficult to learn. Spring is referred to as “magic” because its abilities at times seem impossible - until you look behind the curtain and understand what’s going on! Here are a few of the major features that Spring takes advantage of for it’s so-called magic.

## Auto-configuration: Classloader and Resource Management
Spring Boot introduced the concept of [Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-auto-configuration.html){:target="_blank"} which greatly reduced the learning curve of Spring. It leverages existing Java technology such as the Classloader and Resource Bundler to perform analysis on “what is present in the application” and make decisions based on it. For example if you have a MySQL Driver in your application then you’ll probably be connecting to a MySQL database so the auto-configuration will set that up for you. If you have Tomcat dependencies then you’ll probably be starting up a Tomcat container so the auto-configuration will do that for you. 

Auto-configuration is not just restricted to Spring developers, it is available for everyone to [create their own Auto-configuration libraries](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html){:target="_blank"} to make their own “magic” happen automatically. 

### Classloader
A Java Classloader is the essential component that literally loads your classes into the JVM. Using this concept Spring can take advantage of knowing what’s present in your program (“on the classpath”) and make intelligent decisions about how and what to run. 

<i class='fas fa-bookmark'></i> Read: [Java Classpath (wikipedia.org)](https://en.wikipedia.org/wiki/Classpath_(Java)){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Java Classloader (wikipedia.org)](https://en.wikipedia.org/wiki/Java_Classloader){:target="_blank"}

### Resources
Spring will scan the resources (non-class files) on the classpath and make decisions on things. Two good examples of this are detecting the presence of properties and automatically loading them into your program, or detecting SQL files and automatically seeding your database for you. Features like this are constanting being added to make the development experience more efficient.

<i class='fas fa-bookmark'></i> Read: [Resources (spring.io)](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/resources.html#resources){:target="_blank"}

## Reflection and Annotations
Spring relies heavily on Reflection to do almost everything.

<i class='fas fa-bookmark'></i> Read: [Java Reflection Example Tutorial (journaldev.com)](https://www.journaldev.com/1789/java-reflection-example-tutorial){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Call Methods at Runtime Using Java Reflection (baeldung.com)](http://www.baeldung.com/java-method-reflection){:target="_blank"}

[Annotations](https://en.wikipedia.org/wiki/Java_annotation){:target="_blank"} have evolved to be the standard way we provide “advice” to our Java programs. Spring uses annotations in place of external configuration files to drive the wiring of components.

<i class='fas fa-bookmark'></i> Read: [Lesson: Annotations (oracle.com)](https://docs.oracle.com/javase/tutorial/java/annotations/){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Spring Framework Annotations (springframework.guru)](https://springframework.guru/spring-framework-annotations/){:target="_blank"}

## Proxies
A [Proxy](https://en.wikipedia.org/wiki/Proxy){:target="_blank"} is the modeling of a relationship where one component is configured to act on-behalf-of another. For any given action the proxy will make a decision on what to do.

* Delegate the request as-is to the target
* Augment the request before delegating to the target
* Fulfill the request entirely by itself
* Ignore the request

You will learn how Spring manages your code as components called Beans (or “Managed Beans”). These Beans act as a Proxy to your code allowing Spring to make decisions on every action taken (i.e., function calls, auditing, transaction boundaries, error handling).

Spring Beans and the Proxy role they play in your application is the center stage in the Spring “magic show”.

> Side note that Spring won't *always* create a Proxy for your objects. It is intelligent enough to understand when it needs to and when it doesn't. If you're not doing anything interesting with the object anywhere in your application then it will not bother creating the proxy.

<i class='fas fa-play'></i> Watch: [Understanding Spring Bean Factory](https://www.youtube.com/watch?v=xlWwMSu5I70){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Understanding Proxy Usage In Spring (spring.io)](https://spring.io/blog/2012/05/23/transactions-caching-and-aop-understanding-proxy-usage-in-spring){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Debunking Myths Proxies Impact Performance (spring.io)](https://spring.io/blog/2007/07/19/debunking-myths-proxies-impact-performance/){:target="_blank"}