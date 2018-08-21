---
title:  "Beans Injection"
permalink: /basics/bean-injection
---

<i class="fas fa-book-reader"></i> Spring Framework: [Dependencies](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-dependencies){:target="_blank"}

Let's revist the concept of dependency injection and explore a few approaches with Spring. 

# Accessing Beans with Dependency Injection
Once your Beans are registered with the ApplicationContext you need a way to retrieve them and this is done through Dependency Injection. Spring provides several ways to accomplish this but we tend to use one of these techniques: constructor, property, or directly from the ApplicationContext.

> **Important:** Dependency Injection is completely Spring managed, that is you *get Beans from other Beans*. The whole concept is that you retrieve a Bean from the Spring ApplicationContext and it's the containers job to construct and wire the dependencies into your Bean before giving it back to you. If you're creating a component via the **new** operator then it is "unmanaged" and Spring is unaware of it so any Spring constructs defined in your class will not work at all.

## 1. Injection via Constructor
The best way to wire you dependencies together are by defining them in your constructor. The reason this is favored is because it's very obvious what your dependencies are. Additionally it makes it easy to Unit Test your code because you have the ability to inject Mocks into the constructor. 

<i class='far fa-bookmark'></i> Read: [**stackoverflow.com:** Explain why constructor inject is better than other options](https://stackoverflow.com/questions/21218868/explain-why-constructor-inject-is-better-than-other-options){:target="_blank"}

```kotlin
@Service
class UserService { /* some code */ }

/** 
  * Spring will create a UserController Bean for you and all the dependencies
  * needed as well (i.e., userService)
  */ 
@RestController
class UserController(

    /** 
      * Spring will automatically detect that a reference to another Bean 
      * called userService is required and inject it here for you. 
      */ 
    var userService: UserService
  
){
    @RequestMapping("/user/{id}")
    fun findUserById(@RequestParam id: Long) = userService.findById(id) // now you can use it in your class
}
```

Now to test the UserController you could easily Mock out the UserService.

```kotlin
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest{

    lateinit var userController
    lateinit var userService

    @Before fun setup() {
      // easily inject a fake mock of the UserService
      userService = Mockito.mock(UserService::class.java)
      userController = UserController(userService)
    }

    @Test
    fun `easy test because of constructor injection`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
        
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

## 2. Injection via Property with @Autowired
The favored alternative to constructor injection is property level injection. This approach illustrates a lot more of the Spring magic because it's using reflection to discover properties of your class that need to be provided by Spring. Surprising to many people it can even inject **private** properties into your class. 

This approach is very convenient in our class but things change dramatically when you want to test it.   

<i class='far fa-bookmark'></i> Read: [**vojtechruzicka.com:** Dangers of Field Injection](http://vojtechruzicka.com/field-dependency-injection-considered-harmful/){:target="_blank"}

```kotlin
@Service
class UserService { /* some code */ }

/* 
 * Spring will create a UserController Bean for you and all the dependencies
 * needed as well (i.e., userService)
 */ 
@RestController
class UserController{

    /* 
     * Spring will use reflection to detect that this property is a reference to 
     * another Bean called userService and inject it here for you directly after 
     * the UserController is initialized by Spring. 
     */ 
    @Autowired
    lateinit var userService: UserService

    @RequestMapping("/user/{id}")
    fun findUserById(@RequestParam id: Long) = userService.findById(id) // now you can use it in your class
}
```

> [lateinit](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties-and-variables){:target="_blank"} is a Kotlin construct and is confusing for those new using the language with Spring. This is required because Kotlin is very strict with null safety checking. There is a short period of time where the userService property will actually be null, refer back to the Spring Bean Lifecycle section to learn more. Spring will first create your Object and then use reflection immediately after to inject these properties, lateinit essentially tells Kotin to ignore the null safety checks for this reference. 

Testing code written this way is much harder because you really don't have a good clean way to override the value with a Mock. There are two general approaches to this - define another bean as the @Primary and override it in the ApplicationContext, or using Mockito and the @InjectMocks annotation.

### Mocking @Autowired Properties with @Primary
The [@Primary](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Primary.html){:target="_blank"} annotation allows you to define multiple beans of the same and give Spring a hint at deciding which one to choice by default if no [@Qualifier](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/annotation/Qualifier.html){:target="_blank"} is used. 

This allows you to shadow a Bean in your application with a Mock. In order to do this you need to define a test configuration and load up another Bean of the exact same type and name. 

```kotlin
/**
  * Since we're declaring a new Configuration file just for testing we have to make 
  * the Spring test loader aware of it by refering to it in this annotation. Notice 
  * that you have also include your main class because once your declare the classes
  * attribute it overides the location of all configuration files!
  */ 
@SpringBootTest(classes = [DemoApplication::class, MyTest.TestConfig::class])
@RunWith(SpringRunner::class)
class MyTest{

    /**
      * This is now needed to define another Bean of the same type and mark it
      * as @Primary which essentially shadows the original one. 
      */
    class TestConfig {
      @Bean
      @Primary
      fun userService() = Mockito.mock(UserService::class.java)
    }

    /**
      * Now we have to rely on Spring to provide us references to test so we
      * inject them here. This is a big step away from "unit" testing. 
      */ 
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var userController: UserController

    @Test
    fun `test with mock primary bean`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))

        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

### Mocking @Autowired Properties with Mockito @InjectMocks
Mockito can eliminate that nasty bit of TestConfig code using @Mock and @InjectMocks. The concept is same as with @Primary but instead of letting Spring inject the Beans into your test class you let Mockito do it. 

* **@Mock** - Is just like Mockito.mock(..) but registers the Mock to be used with @InjectMocks
* **@InjectMocks** - Is just like @Autowired but Mockito will swap out any beans with their matching Mocks declared by @Mock

Although powerful this approach can be a little dangerous. 

<i class='far fa-bookmark'></i> Read: [**baeldung.com:** Getting Started with Mockito @Mock, @Spy, @Captor and @InjectMocks](http://www.baeldung.com/mockito-annotations){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**stackoverflow.com:** @Mock and @InjectMocks](https://stackoverflow.com/questions/16467685/difference-between-mock-and-injectmocks){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [**tedvinke.wordpress.com:** Why You Should Not Use InjectMocks Annotation to Autowire Fields](https://tedvinke.wordpress.com/2014/02/13/mockito-why-you-should-not-use-injectmocks-annotation-to-autowire-fields/){:target="_blank"}

```kotlin
@RunWith(SpringRunner::class)
@SpringBootTest
class MyTest{

    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var userController: UserController

    @Test
    fun `test`(){
        Mockito.`when`(userService.findById(Mockito.any(Long::class.java)))
          .thenReturn(User(1, "Fake User"))
          
        // continue with test
        var fakeUser: User = userController.findUserById(1);
    }

}
```

## 3. Injection via ApplicationContext
This approach is typically used when you are dynamically interacting with Beans during runtime, in other words you're unsure of which Bean you need so you need to look it up. For example if you're processing requests and need a corresponding transformer for different types of requests you wouldn't want to inject a reference to every type of transformer.

Let's define a few types of tranformers:

```kotlin
interface BrowserTransformer{
    fun transform(inbound: String): String
}

@Component class ChromeTransformer : BrowserTransformer {
    override fun transform(inbound: String): String = "Chrome:".plus(inbound)
}

@Component class FirefoxTransformer : BrowserTransformer{
    override fun transform(inbound: String): String = "Firefox:".plus(inbound)
}

@Component class IETransformer : BrowserTransformer{
    override fun transform(inbound: String): String = "IE:".plus(inbound)
}
```

Now you can retrieve them directly through the ApplicationContext:

```kotlin
@SpringBootApplication
class DemoApplication(var appContext: AbstractApplicationContext) : ApplicationRunner{
    override fun run(args: ApplicationArguments?) {

        var xform = appContext.getBean("firefoxTransformer", BrowserTransformer::class.java)
        println(xform.transform("Hello"))
    }

}

@RestController
class EchoController(var appContext: AbstractApplicationContext) {
  
  @RequestMapping("/echo")
  fun echo(str: String){
    var userAgent = getUserAgent()
    
    // this will get the corresponding bean based on its name
    var xform = appContext.getBean("${userAgent}Transformer", BrowserTransformer::class.java)
    
    return xform.transform(str)
  }
  
  fun getUserAgent(): String {
    // assume this code will return the String: "IE", "firefox", or "chrome"
  }
  
}

```

Testing code written this way can only be done by shadowing the original bean with @Primary in a TestConfig. 

## 4. Injection via ObjectFactory 
Spring provides a class called [ObjectFactory](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/ObjectFactory.html){:target="_blank"} that can be used to dispense beans, this is perfect @scope("prototype") beans where you want a new instance each time. You simply inject a generic typed reference of ObjectFactory.

```kotlin
import org.springframework.beans.factory.ObjectFactory

/**
  * Bean to represent a single XLS row, we want a new instance for each row. 
  */
@Component 
@Scope("prototype")
class XLSRow { /*code*/ }

/**
  * Bean to iterate through each XLS row and process it.
  */ 
@Component
class XLSProcessor(
    var xlsRowFactory : ObjectFactory<XLSRow>
) {
  
  fun processXls(xlsFile: XLSFile){
     for(Row r : xlsFile.getRows()){
        // spring will dispense a new bean each time this is called.
        var xlsrow = xlsRowFactory.getObject()
     }
  }

}
```

**Read:** [**stackoverflow.com:** How to Produce prototype objects](https://stackoverflow.com/questions/6136261/how-to-produce-prototype-objects-from-singleton-design-help-needed){:target="_blank"}

# Exercises
Try out this exercise to get a feel for the Application Context and declaring Beans.

* **Exercise:** [**github.com:** Journey Through Spring: basic-beans](https://github.com/JahnelGroup/journey-through-spring/tree/master/src/basic-beans){:target="_blank"}
