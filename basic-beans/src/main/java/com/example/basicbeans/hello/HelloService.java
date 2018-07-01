package com.example.basicbeans.hello;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HelloService {

    public String sayHello(Optional<String> name){
        return sayHello(name.orElse("nobody"));
    }

    public String sayHello(String name){
        return "Hello " + name;
    }

}
