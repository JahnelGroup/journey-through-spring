---
title:  "Spring Boot Starters"
permalink: /intro/gettingstarted-spring-starters

sidebar:
  nav: intro
---

We cannot deny that Spring has a learning curve but once you’re over the hump it is all about making the developer more efficient. It is designed to give a developer the ability to deliver software rapidly.

## Spring Boot Starters

Spring Boot developers have created “starters” that are a slightly different concept from “seed projects”. A seed-project consists of a codebase that has been either generated or hand-coded to be the base of your application. They usually come in the form a github repository that you fork and start from. Spring Starters however are just like regular dependencies that you’d pull into your application and are kind of hidden from the rest of your program. These starters are built upon the auto-configuration feature discussed above in the Understanding the “Magic” of Spring section. Their code is ran when the application starts up and they will create Beans, start Web Servlets, establish connection pooling to a database, and do many more things. 

<i class='fas fa-bookmark'></i> Read: [Spring Starters (spring.io)](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-starter){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Intro to Spring Boot Starters (baeldung.com)](http://www.baeldung.com/spring-boot-starters){:target="_blank"}

## Spring Boot Executable “fat Jar”

A “fat Jar” or “Uber Jar” is a single Jar file that contains everything needed to run an application. The most powerful thing to realize here is that you can actually run a Spring Boot jar file and it will start up a Web Container from within the Jar file. You would normally have to deploy your application into a separate Webserver but with Spring Boot the Webserver is actually running within the Jar file!

This concept extends to other components like H2, Elasticsearch, Redis, etc. This is great for rapid development but eventually when a product matures we will change the architecture to be more production-like with external services. It’s all about getting up and running as fast as possible in the beginning. 

<i class='fas fa-bookmark'></i> Read: [What is a fat Jar? (stackoverflow.com)](https://stackoverflow.com/questions/19150811/what-is-a-fat-jar){:target="_blank"}<br/>
<i class='fas fa-bookmark'></i> Read: [Creating an Executable Jar (spring.io)](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-executable-jar){:target="_blank"}