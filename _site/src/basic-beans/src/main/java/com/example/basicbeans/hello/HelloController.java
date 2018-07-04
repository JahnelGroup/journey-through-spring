package com.example.basicbeans.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {

    /**
     * Injection via property with @Autowired
     */
    @Autowired private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam Optional<String> name){
        return helloService.sayHello(name);
    }

}