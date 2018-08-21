package com.jahnelgroup.basics.di

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent

@SpringBootApplication
class App : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        event.applicationContext.beanDefinitionNames.forEach {
            println("Bean: $it")
        }
        println("=".repeat(100))
        println("Found ${event.applicationContext.beanDefinitionNames.size} Beans")
    }

}

fun main(args: Array<String>) {
    // start the IoC container
    runApplication<App>(*args)
}
