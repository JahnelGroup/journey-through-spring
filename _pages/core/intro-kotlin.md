---
title:  "Spring and Kotlin"
permalink: /core/intro-kotlin
---

[Kotlin](https://kotlinlang.org/){:target="_blank"} was created by [JetBrains](https://www.jetbrains.com/){:target="_blank"} and quickly adopted by the Android community. It's attractive for many reasons and is frequently compared against [Scala](https://www.scala-lang.org/){:target="_blank"}, [Clojure](https://clojure.org/){:target="_blank"} and [Groovy](http://groovy-lang.org/){:target="_blank"}. All of these languages are excellent alternatives and complementary to Java, each with their strengths and weaknesses. Understanding the differences between these languages are out-of-scope for this course and I encourage you to [read more](https://www.google.com/search?ei=Wd5AW6fADM7IsAW5_YzoAQ&q=scala+vs+groovy+vs+kotlin&oq=scala+vs+groovy+vs+kotlin&gs_l=psy-ab.3..0i67k1j0i22i30k1.2591.11747.0.12308.20.13.7.0.0.0.119.871.12j1.13.0....0...1c.1.64.psy-ab..0.20.895...33i160k1j33i22i29i30k1.0._ufRtvCb30U){:target="_blank"} on your own.

As of 2017 the Spring community officially declared [support for Kotlin in Spring Framework 5.0](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0){:target="_blank"} with the others being Java and Groovy. I personally prefer Kotlin over Groovy so this course will features examples written in Kotlin. It does an excellent job at inheriting the best features of various languages and eliminates a significant amount of the verbosity found in Java. It’s powerful features and 100% interoperability with Java make a very compelling argument as a language replacement.

> Kotlin is a general purpose, open source, statically typed “pragmatic” programming language for the JVM and Android that combines object-oriented and functional programming features. It is focused on interoperability, safety, clarity, and tooling support. Versions of Kotlin for JavaScript (ECMAScript 5.1) and native code (using LLVM) are in the works.
>
> &mdash; <cite>[infoworld.com](https://www.infoworld.com/article/3224868/java/what-is-kotlin-the-java-alternative-explained.html){:target="_blank"}</cite>

<i class='fas fa-bookmark'></i> Read: [Comparison to Java Programming Language (kotlinlang.org)](https://kotlinlang.org/docs/reference/comparison-to-java.html){:target="_blank"}

<i class='fas fa-bookmark'></i> Read: [Using Kotlin for Server-side Development (kotlinlang.org)](https://kotlinlang.org/docs/reference/server-overview.html){:target="_blank"}

Here are a few key differences from Java that will help you follow along in this Journey.

## Optional Semicolons

Semicolons are optional.

```java
// Java
System.out.println("Hello, World!");
```

```kotlin
// Kotlin
println("Hello, World!")
```

## Type Inference

Java and Kotlin are considered strict static languages but Kotlin has type inference. This means if the compiler can determine the type of a variable then you can omit it. The type is defined on the right side of the variable name instead of the left.

```java
// Java
Integer num = 1;
String str = "Hello";
```

```kotlin
// Kotlin
var num: Int = 1   // explictly setting the type
var str = "Hello"  // type is inferred
```

## Null Safety

The most infamous exception in Java is the [NullPointerException](https://stackoverflow.com/questions/218384/what-is-a-nullpointerexception-and-how-do-i-fix-it){:target="_blank"}. It happens often and worse is that Java does almost nothing to help you avoid them. Kotlin has taken a very defensive approach to **null** and actually makes you declare if a reference can potentially be null.

```kotlin
// Kotlin

// will not compile: Null can not be a value of a non-null type String
var str: String = null

// this is okay: it must be explictly declared a nullable type with the question mark
var str: String? = null
```

### Spring Injection

As you'll learn through this course, Spring provides something called dependency injection that introduces a problem with Kotlin's null safety checks. In order to get around this issue Kotlin provides a [lateinit](https://kotlinlang.org/docs/reference/properties.html#late-initialized-properties-and-variables) keyword that relaxes this check.

```java
// Java
@Autowired
UserService userService;
```

```kotlin
// Kotlin
@Autowired
lateinit var userService: UserService
```

## Instantiating Objects

In Java we use the **new** keyword to instantiate an Object but it does not exist in Kotlin so we simply omit it.

```java
// Java
User user = new User(1, "Steven");
```

```kotlin
// Kotlin
var user = User(1, "Steven")
```

## Class Declaration

Kotlin's syntax for [Class and Inheritance](https://kotlinlang.org/docs/reference/classes.html){:target="_blank"} are slightly different than Java.

### Inheritance

In Java we have the **extends** and **implements** keywords for class and interfaces respectively, but that does not exist in Kotlin so we use a colon **:** after the class name for both. Kotlin retains the single inheritance principal from Java so if you're extending a class it must the first one in the list.

```kotlin
// Kotlin

// extends AbstractParentClass and implements MyInterface1, MyInterface2
class ChildClass : AbstractParentClass, MyInterface1, MyInterface2 { }

// implements MyInterface1
class ChildClass : MyInterface1 { }
```

### Constructors

Kotlin can have one primary and multiple secondary constructors. The primary constructor comes directly after the name of the class.

```kotlin
// Kotlin

// single primary constructor
class User(firstName: String) { }

// primary constructor and one secondary constructor
class User(firstName: String) : UserService {
  constructor(firstName: String, lastName: String) : this(firstName) { }
}
```