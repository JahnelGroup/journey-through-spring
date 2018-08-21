---
title:  "Bean Scopes and Proxies"
permalink: /basics/bean-scopes-proxies
---

<i class="fas fa-book-reader"></i> Spring Framework: [Bean scopes](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-factory-scopes){:target="_blank"}

## Bean Scope
The concept of [Scope](https://en.wikipedia.org/wiki/Scope_(computer_science)){:target="_blank"} is fundamental to computer programming and it’s no surprise that its found in Spring as well. As you delegate the control over the creation of your Objects to Spring you have the ability to advise Spring on scope with @Scope.

| Scope                   | Alias             | Description                                                     |
| ----------------------- | ----------------- | --------------------------------------------------------------- |
| singleton **(default)** | None, default     | A single instance is created                                    |
| prototype               | None              | A new instance is created each time the bean is referenced      |
| request                 | @RequestScope     | A new instance is created once per web request (web-aware)      |
| session                 | @SessionScope     | A new instance is created once per user web session (web-aware) |
| application             | @ApplicationScope | A new instance is created once per ServletContext (web-aware)   |
| websocket               | None              | A new instance is created once per WebSocket (web-aware)        |
| *\<custom>*             | None              | It is possible to define your own scope rules                   |

```kotlin
@Component 
@Scope("session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserSessionContext { }

// or this is the same
@Component 
@SessionScope
class UserSessionContext { }
```

<i class='far fa-bookmark'></i> Read: [**spring.io:** Bean Scopes](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-factory-scopes){:target="_blank"}<br/>
<i class='far fa-bookmark'></i> Read: [**baeldung.com:** Spring Bean Scopes](http://www.baeldung.com/spring-bean-scopes){:target="_blank"}

## Bean Proxy Mode
There are two fundamental types of proxies **JDK** (by interface) and **CGLIB** (by class). It's best practice that you inject by interface as opposed to class because it's easier for Spring. If you inject by the target class then Spring needs to do some extra work to accomplish it and is arguably a performance impact although there is debate about that.

| ScopedProxyMode     | Description                                                     |
| ------------------- | --------------------------------------------------------------- |
| DEFAULT             | Typical NO unless configured differently elsewhere.             |
| NO                  | Do not create a scoped proxy.                                   |
| INTERFACES          | JDK Based Proxy                                                 |
| TARGET_CLASS        | CGLIB Based Proxy                                               |

<i class='far fa-bookmark'></i> Read: [**spring.io:** Scoped beans as dependencies](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-factory-scopes-other-injection){:target="_blank"}