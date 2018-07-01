package com.example.basicvalidations

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator

@Configuration
class Config{

    @Bean
    fun messageSource(): MessageSource {
        val bean = ReloadableResourceBundleMessageSource()
        bean.setBasename("classpath:/messages")
        bean.setDefaultEncoding("UTF-8")
        return bean
    }

    @Bean
    fun validator(): javax.validation.Validator {
        val factory = LocalValidatorFactoryBean()
        factory.setValidationMessageSource(messageSource())
        return factory
    }

    @Bean
    fun resourceBundleMessageInterpolator() : ResourceBundleMessageInterpolator =
            ResourceBundleMessageInterpolator(MessageSourceResourceBundleLocator(messageSource()))

}