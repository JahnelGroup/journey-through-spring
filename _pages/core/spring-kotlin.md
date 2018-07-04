---
title:  "Spring and Kotlin"
permalink: /core/spring-kotlin
---

Kotlin was originally built by JetBrains and quickkly adopted by the Android community because it solves many problems that exist in Java. It does an excellent job at inheriting the best features of various languages and eliminates a significant amount of the verbosity found in Java. It’s powerful features and 100% interoperability with Java make a very compelling argument as a language replacement.

> *Kotlin is a general purpose, open source, statically typed “pragmatic” programming language for the JVM and Android that combines object-oriented and functional programming features. It is focused on interoperability, safety, clarity, and tooling support. Versions of Kotlin for JavaScript (ECMAScript 5.1) and native code (using LLVM) are in the works. - *[**infoworld.com:** What is Kotlin](https://www.infoworld.com/article/3224868/java/what-is-kotlin-the-java-alternative-explained.html)**

* **Read:** [**infoworld.com:** What is Kotlin? The Java alternative explained](https://www.infoworld.com/article/3224868/java/what-is-kotlin-the-java-alternative-explained.html)
* **Read:** [**kotlinlang.org:** Using Kotlin for Server-side Development](https://kotlinlang.org/docs/reference/server-overview.html)

This journey will focus primarily on Spring Boot and the example code will be written in Kotlin.

* **Read:** [**kotlinlang.org:** Comparison to Java Programming Language](https://kotlinlang.org/docs/reference/comparison-to-java.html)
* **Read:** [**spring.io:** Introducing Kotlin support in Spring Framework 5.0](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)

## Highlight a few differences from Java
If you're new to Kotlin here are a few main differences from Java that will help you follow along in this Journey.

### Optional Semicolons
Semicolons are optional.

**Java**
```java
System.out.println("Hello, World!");
```    
**Kotlin**
```kotlin
println("Hello, World!")
``` 

### Null Safety
The most infamous exception in Java is the [NullPointerException](https://stackoverflow.com/questions/218384/what-is-a-nullpointerexception-and-how-do-i-fix-it). It happens very often and the problem with Java is that it does almost nothing to help you avoid them. Kotlin has taken a very defensive approach to **null** and actually makes you declare if a reference can potentially be null. 

### Type Inference
Both Java and Kotlin are considered strict static languages but Kotlin has type inference. This means if the compiler can determine the type of a reference without a doubt then you can omit it. Also the type is defined on the right side of the variable name instead of the left.

* **Read:** [**tutsplus.com:** Kotlin From Scratch: Variables, Basic Types, and Arrays](https://code.tutsplus.com/tutorials/kotlin-from-scratch-variables-basic-types-arrays-type-inference-and-comments--cms-29328)

**Java**
```java
Integer num = 1;
String str = "Hello";
```    
**Kotlin**
```kotlin
var num = 1
var str: String = "Hello"
```

### Instantiating Objects
In Java we use the **new** keyword to instantiate an Object, but that does not exist in Kotlin so we simply omit it. 

**Java**
```java
User user = new User(1, "Steven");
```    
**Kotlin**
```kotlin
var user = User(1, "Steven")
```

### Class Declaration 
The two things to be aware of are how you declare *inheritance* and *constructors*.

* **Read:** [**kotlinlang.org:** Classes and Inheritance](https://kotlinlang.org/docs/reference/classes.html)

#### Inheritance 
In Java we have the **extends** and **implements** keywords for class and interfaces respectively, but that does not exist in Kotlin so we use a colon **:** after the class name for both. Kotlin is still single inheritance so if you're extending a class it must the first one in the list.

```kotlin
// extends AbstractParentClass and implements MyInterface1, MyInterface2
class ChildClass : AbstractParentClass, MyInterface1, MyInterface2 { }

// implements MyInterface1
class ChildClass : MyInterface1 { }
```

#### Constructors
Kotlin can have one primary and one or more secondary constructors. The primary constructor comes directly after the name of the class.

```kotlin
// single primary constructor
class User(firstName: String) { }

// primary constructor and one secondary constructor
class User(firstName: String) : UserService {
  constructor(firstName: String, lastName: String) : this(firstName) { }
}
```