---
title:  "Bean Configuration"
permalink: /basics/bean-configuration
---

Objects are the key element in Java programming and the Spring Framework handles them in a very special way. Unlike a regular Java Object that is created with the **new** operator and then used, a Spring Object must be registered with the ApplicationContext first. When an Object is registered with the ApplicationContext it is referred to as a [Bean](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-definition){:target="_blank"} (or “Managed Bean” or “Component”). When Spring manages an Object as a Bean it is creating a Proxy around your object and can do very interesting things with it.

## How to configure a Bean
<i class="fas fa-book-reader"></i> Spring Framework: [Bean overview](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-definition){:target="_blank"}

Beans can be configured in three primary ways: XML, Java, or Annotations. We will focus on Annotation based configuration.

<i class='far fa-bookmark'></i> Read: [**spring.io:** Annotation-based container configuration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-annotation-config){:target="_blank"}

Spring provides a few different annotations to drive the creation of beans. Ultimately Spring will use **@Bean** or **@Component** to create a Bean. Here are the most common ways to define Beans.

| Annotation      | Location | Description                                            |
| --------------- | -------- | ------------------------------------------------------ |
| @Configuration  | class    | Indicates that this class defines @Bean's to be        |
| @Bean           | method   | Creates a Bean from the return value of the method     |
| @Component      | class    | Instruct Spring to create a Bean out of the class      |
| @Service        | class    | Is a @Component but Stereotyped as a Service           |
| @Repository     | class    | Is a @Component but Stereotyped as a Repository        |
| @Controller     | class    | Is a @Component but Stereotyped as a Controller        |
| @RestController | class    | Same as @Controller but meant REST end-points          |

<i class='far fa-bookmark'></i> Read: [Advantages of using spring stereotypes (stackoverflow.com)](https://stackoverflow.com/questions/16051656/advantages-of-using-spring-stereotypes){:target="_blank"}<br/>
<i class='far fa-bookmark'></i> Read: [Difference between @Component, @Repository, @Service (stackoverflow.com)](https://stackoverflow.com/questions/6827752/whats-the-difference-between-component-repository-service-annotations-in){:target="_blank"}<br/>
<i class='far fa-bookmark'></i> Read: [Difference between @Controller and @RestController (stackoverflow.com)](https://stackoverflow.com/questions/25242321/difference-between-spring-controller-and-restcontroller-annotation){:target="_blank"}<br/>
<i class='far fa-bookmark'></i> Read: [@RestController vs @Controller (genuitec.com)](https://www.genuitec.com/spring-frameworkrestcontroller-vs-controller/){:target="_blank"}

```kotlin
// Declare a Singleton Bean - one instance for the entire application
@Component
class UserTransformer { }

// Declare a Request Scope Bean - one instance per Web Request
@Component
@Scope("request", proxyMode = ScopedProxyMode.INTERFACES)
class UserWebRequestContext : UserRequestContext { }

// Declare a class that defines definition of Beans
@Configuration
class MyConfig {

  // Declare a Session Scope Bean - one instance per Web Session
  @Bean 
  @Scope("session")
  fun userSessionContext() = UserSessionContext()

}

// Declares a Bean with the Service stereotype
@Service
class UserService { }

// Declares a Bean with the Repository stereotype
@Repository
class UserRepository { }

@RestController
class HelloController {
  @RequestMapping("/hello")
  fun hello() = "Hello, World!"
}

```

## Bean Lifecycle
<i class="fas fa-book-reader"></i> Spring Framework: [Lifecycle callbacks](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-factory-nature){:target="_blank"}

As Spring Beans are created they follow a Lifecycle where you can hook-in callback listeners to do interesting things when the Beans are first created and destroyed. 

<i class='far fa-bookmark'></i> Read: [**thejavaprogrammer.com:** Spring Bean Life Cycle](https://www.thejavaprogrammer.com/spring-bean-life-cycle/){:target="_blank"}

A common use-case is to validate that certain properties are set on your Bean after it is created.

```kotlin
@Component
class ConnectionManager(
    @Value("\${connection.url}") var url: String = "") {

    @PostConstruct
    fun init(){
        Assert.isTrue(!url.isNullOrBlank(), "url must be set")
        Assert.isTrue(url.startsWith("http://"), "http is the only support protocol")
    }
}
```