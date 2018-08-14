---
title:  "Events"
permalink: /core/core-events
---

Events
======
The concept of [Events](https://en.wikipedia.org/wiki/Event_(computing)) is fundamental to computer programming and allows us to design components with loose coupling and high cohesion. Spring’s context is highly event driven and exposes this feature for you to listen and react to events as well as fire your own. 

* **Read:** [**wikipedia.org:** Event-driven programming](https://en.wikipedia.org/wiki/Event-driven_programming)
* **Read:** [**stackoverflow.com:** What does 'low in coupling and high in cohesion' mean](https://stackoverflow.com/questions/14000762/what-does-low-in-coupling-and-high-in-cohesion-mean)
* **Read:** [**spring.io:** Application Events and Listeners](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-application-events-and-listeners)
* **Read:** [**baeldung.com:** Spring Events](http://www.baeldung.com/spring-events)
* **Read:** [**spring.io:** Standard and Custom Events](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-functionality-events)

## ApplicationEvent
Spring provides a class called [ApplicationEvent](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEvent.html) to faciliate the processing of events. Your events do not need to extend this class but it is good practice. Its constructor takes a single argument of type Object which is intended to be the *source* of the event. 

### What are good Events?
An event *source* is the Object for which the event is trigger against. For example, say you want to send an E-Mail to every user that signs up for your system. The event would be something like *UserSignedUpEvent* and the source would be the *User* object.

Events are things that *have happened* and they are past tense. UserLoggedInEvent states that a user has already logged in and you're being notified of the fact. Do not use events in place of direct method invocation, that's not the idea. They are more about a change to the conceptual design of your application and building a set of components that react to each other in a choreographed way.

**Read:** [**stackoverflow.com:** Orchestration vs. Choreography](https://stackoverflow.com/questions/4127241/orchestration-vs-choreography)
**Read:** [**thoughtworks.com:** Scaling Microservices with an Event Stream](https://www.thoughtworks.com/insights/blog/scaling-microservices-event-stream)

### Events are POJO's
With the except of extending ApplicationEvent for best practice, the event class it self should just be a plain old java object, nothing fancy, just a wrapper of data. 

```kotlin
// Model of a simple User 
data class User(
        var id: Long = 0L,
        var username: String = "",
        var email: String = ""
)

// Event to be raised when a new user signs up
class UserSignedUpEvent(user: User) : ApplicationEvent(user)
```

## Raising Events with ApplicationEventPublisher
Spring provides a convenient means to raise events with [ApplicationEventPublisher](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html). Inject this into any location where you need to raise events and call publish!

```kotlin
@Component
class UserService(
        var userRepo: UserRepository,
        var appEventPublisher: ApplicationEventPublisher){

    /**
      * Creates and saves a new user to the database, after succeeding it will
      * raise the UserSignedUpEvent for other interested components to react. 
      */ 
    fun register(username:String, email:String){
        var user = userRepo.save(User(username, email))
        applicationEventPublisher.publishEvent(UserSignedUpEvent(user))
    }
}
```

## Listening for Events
You can listen for events by either implementing the [ApplicationListener](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html) interface or by the [@EventListener](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/event/EventListener.html) annotation. As always don't forget that you class must be a Bean or else Spring will not have any control over it (here we are defining @Service).

Either approach is fine but the interface implementation makes it a bit more obvious and easier to search on. 

### ApplicationListener Interface
```kotlin
@Service
class EmailService : ApplicationListener<UserSignedUpEvent> {
    override fun onApplicationEvent(event: UserSignedUpEvent) {
        var user = userSignedUpEvent.source as User
        // send out the email
    }
}
```
### @EventListener Annotation
```kotlin
@Service
class EmailService {

    @EventListener
    fun sendWelcomeEmail(userSignedUpEvent: UserSignedUpEvent){
        var user = userSignedUpEvent.source as User
        // send out the email
    }

}
```

## Debugging Events
One option to debug events is have Spring print out useful information for you. There two interesting properties that you can set to print out all the registered EventListeners once at the start of the application, and other to have Spring print everytime it is publishing an event. 

Put these in your **application.properties** file.

```properties
# Trace the publishing of events (i.e., Publishing event in ...)
logging.level.org.springframework.context.annotation=TRACE

# Print out all the EventListeners at the start of the application.
logging.level.org.springframework.context.event.EventListenerMethodProcessor=TRACE
```

## Async Events
**Spring Events are synchronous by default!** This something comes as a shock to people but you must realize that making something automatically async is actually quite dangerous. 

You can easily make an EventListener asynchronous by using the [@Async](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Async.html) annotation. Be warned though that there is more to this little annotation and you should understand how it works before using it. 

* **Read:** [**spring.io:** Asynchronous Listeners](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-functionality-events-async)
* **Read:** [**baeldung.com:** Spring Async](http://www.baeldung.com/spring-async)

## Transactional Events
Transactions are discussed in another section but it's important to understand that they play a role with EventListeners. You have the abilitiy to advise Spring on when and how to run your listeners with respect to transaction that may be going on. To do this you essentially swap out @EventListener with [@TransactionalEventListener](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/event/TransactionalEventListener.html). 

An example of this is would be a MailService listener that should send a welcome E-Mail out only after a user has successfully been commited to the database.

* **Read:** [**dzone.com:** Understanding @TransactionalEventListener](https://dzone.com/articles/transaction-synchronization-and-spring-application)
* **Read:** [**spring.io:** Better application events in Spring Framework 4.2](https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2)

## Ordering of Event Listeners
Ordering things is a rather interesting concept with Spring. When you register multiple things of the same type into the (depending on what it is...) Spring will generally store them as a list **in the order that there detected by Spring during component scanning.** You have a number of ways to advise Spring on the Order of components but the most abstract approach is to use an [@Order](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/core/annotation/Order.html) annotation.  

* **Read:** [**spring.io:** Ordering listeners](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#context-functionality-events-order)
* **Read:** [**stackoverflow.com:** What is the use of @Order annotation in Spring?](https://stackoverflow.com/questions/30328897/what-is-the-use-of-order-annotation-in-spring)
* **Read:** [**javapapers.com:** Spring Order Annotation](https://javapapers.com/spring/spring-order-annotation/)
* **Read:** [**logicbig.com:** Controlling Beans Loading Order](https://www.logicbig.com/tutorials/spring-framework/spring-core/using-depends-on.html)

## Spring Data - Domain Events with @DomainEvents and AbstractAggregateRoot
[Spring Data](http://projects.spring.io/spring-data/) is one of the foundational projects that many other Spring projects are based on. We'll cover it deeper in another section but there is a really neat feature built-in releated to events.

Spring Data follows the concept of [Domain Driven Design ](https://en.wikipedia.org/wiki/Domain-driven_design) and has a concept called Domain Events. Essentially if you have a managed Entity (a special kinda of Bean related to persistence) then Spring Data will look for a field annotated with [@DomainEvents](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/DomainEvents.html) and **automatically publish those events for you when that Entity is acted upon**. Spring Data provides a convenient super class for you to extend with this functionality called [AbstractAggregateRoot](https://github.com/spring-projects/spring-data-commons/blob/master/src/main/java/org/springframework/data/domain/AbstractAggregateRoot.java). 

* **Read:** [**spring.io:** Domain event publication from aggregate roots](https://spring.io/blog/2017/01/30/what-s-new-in-spring-data-release-ingalls#domain-event-publication-from-aggregate-roots)
* **Read:** [**zoltanaltfatter.com:** Publishing domain events from aggregate roots](http://zoltanaltfatter.com/2017/06/09/publishing-domain-events-from-aggregate-roots/)

Take an example where you've modeled a bidding system where people can **Bid** on an **Auction**. I'm going to omit all the JPA annotations for simplicity and showcase the DomainEvents only.

> You can see an entire application built with this on our github page called [jgBay](https://github.com/JahnelGroup/sample-applications/tree/master/jgBay).

Here are two interesting events:

```kotlin
class BidRemovedEvent(auction: Auction, var bid: Bid): AuctionEvent(auction)
class BidAddedEvent(auction: Auction, var bid: Bid): AuctionEvent(auction)
```

Notice although we're adding and removing Bids that the event is actually raised against an Auction. This is because in our modeling the Auction is the [Aggregate](https://martinfowler.com/bliki/DDD_Aggregate.html). 

Here is the **Auction** with the interesting piece of code related to events. 

```kotlin
/**
  * Models an Auction and inherits from AbstractAggregateRoot to leverage DomainEvents.
  */
@Entity
data class Auction : AbstractAggregateRoot(){
    var bids: MutableSet<Bid> = mutableSetOf()
    
    /**
     * Add a new bid to this Auction
     */
    fun addBid(bid: Bid): Auction {
        bid.auction = this
        bids.add(bid)
        
        // this comes from AbstractAggregateRoot
        // when called it will put BidAddedEvent into the DomainEvents list and 
        // Spring Data will raise them automatically when this Entity hits the persistence layer.        
        registerEvent(BidAddedEvent(this, bid))
        
        return this
    }

    /**
     * Remove a bid from this Auction
     */
    fun removeBid(bid: Bid): Auction {
        bids.remove(bid)
        bid.auction = null
        registerEvent(BidRemovedEvent(this, bid)) // this comes from AbstractAggregateRoot
        return this
    }
}
```

Here is an example use of these methods with a Controller. 

```kotlin
@RestController
@RequestMapping("/api/auctions/{id}")
class BidController(val auctionRepo: AuctionRepo){

    @PostMapping("/submitBid")
    fun submitBid(@PathVariable("id") auction: Auction?, @RequestBody bid: Bid) : Bid {
        // omitted validation code 
        
        auction.addBid(bid) // this method is show above and it will queue up the BidAddedEvent
        auctionRepo.save(auction) // Spring Data will detect the BidAddedEvent and raise it here
    }

    // other mappings...
}
```

Now you can react to these just like any other event!

```
@Service
class BidNotificationService {
    // notify previous high bidder that they were just out bid
    @EventListener fun notifyOutbidParties(bidAddedEvent: BidAddedEvent){
        // code ...
    }
}
```

## Spring Integration - ApplicationEventListeningMessageProducer
We cover Spring Integration in another section but it's important to know that it has support for listening to Spring ApplicationEvents with a component called [ApplicationEventListeningMessageProducer](https://docs.spring.io/spring-integration/api/org/springframework/integration/event/inbound/ApplicationEventListeningMessageProducer.html).

* **Read:** [**spring.io:** Spring ApplicationEvent Support](https://docs.spring.io/spring-integration/reference/html/applicationevent.html)

Here is an example of defining the event producer.

```kotlin
@Configuration
class EventsIntegrationConfig {

    /**
     * Listen for the Bid related events and publishes them on a pub/sub channel.
     */
    @Bean
    fun bidEventsProducer(): ApplicationEventListeningMessageProducer {
        val producer = ApplicationEventListeningMessageProducer()
        producer.setEventTypes(
            BidAddedEvent::class.java,
            BidUpdatedEvent::class.java,
            BidRemovedEvent::class.java)
        producer.setOutputChannelName("bidEventsPubSubChannel")
        return producer
    }

}
```