---
title:  "Spring Expression Language (SpEL)"
permalink: /basics/spel
---

Spring Expression Language (SpEL)
======
The Spring Expression Language is a very powerful feature that can be a lifesaver when used correctly and a nightmare when not. It is Spring-specific syntax that adds a level dynamic behavior at runtime. You would typically interact with it in two primary ways - the SpelExpressionParser or through an annotation. 

<i class='far fa-bookmark'></i> Read: [Spring Expression Language](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#expressions){:target="_blank"}
<i class='far fa-bookmark'></i> Read: [Spring Expression Language Guide](http://www.baeldung.com/spring-expression-language){:target="_blank"}

## SpelExpressionParser
Here is an example using SpelExpressionParser from the Spring documentation:

```kotlin
var parser: ExpressionParser = SpelExpressionParser()
var exp: Expression = parser.parseExpression("'Hello World'.concat('!')")
var message: String = (String) exp.getValue() 
println(message) // prints "Hello World!"
```

## Annotation with #{ }
Here is an example doing the same thing with an annotation. SpEL is trigger with syntax **#{ }**.

```kotlin
@Component
class MyConfig {

  @Value("#{'Hello World'.concat('!')}")
  lateinit var hello: String
  
  @PostConstruct fun init() = println(message) // prints "Hello World!"
  
}
```
