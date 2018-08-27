package com.jahnelgroup.basics.di

import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import javax.annotation.PostConstruct

@Component
class BeanHunter(
    // injection via constructor
    val appContext: ApplicationContext
) {

    // injection via property
    @Autowired
    lateinit var coolBean: CoolBean

    @PostConstruct
    fun hunt(){

        // injection via appContext
        var sameCoolBean = appContext.getBean("coolBean")
        Assert.isTrue(coolBean === sameCoolBean, "Beans should be referentially equivalent.")
        println(">> Found Bean: coolBean")

        var ex: Exception? = null
        try {
            appContext.getBean("lonelyClass")
        } catch (e: Exception) {
            ex = e
            println(">> No Such Bean: lonelyClass")
        }
        Assert.isTrue(ex is NoSuchBeanDefinitionException, "LonelyClass should not be a Bean at all!")
    }

}