package com.example.basicbeans.proxy_print_obj_ref;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Defines three Beans all of the same type
 */
@Configuration
public class ProxyBeanConfig {

    @Bean
    MyPOJO singleton(){
        return new MyPOJO();
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    MyPOJO prototype(){
        return new MyPOJO();
    }

    @Bean
    @SessionScope
    MyPOJO session(){
        return new MyPOJO();
    }
}
