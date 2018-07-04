package com.example.basicbeans.proxy_print_obj_ref;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    /**
     * Spring will use the variable names to qualify down and
     * respectively inject the correct references for each.
     *
     * For demonstration I gave the prototype a mismatching variable name
     * but used Spring's @Qualifer to help narrow it down.
     */

    @Autowired
    MyPOJO singleton;
    @Autowired
    MyPOJO session;
    @Autowired @Qualifier("prototype")
    MyPOJO prototypeDifferentName;

    /**
     * The same reference is used globally by everyone.
     */
    @GetMapping("/proxy/singleton")
    String singleton(){ return getResult(singleton); }

    /**
     * Each user session is given their own instance of the session bean.
     */
    @GetMapping("/proxy/session")
    String session()  { return getResult(session); }

    /**
     * Every invocation against the prototype bean is a brand new instance.
     */
    @GetMapping("/proxy/prototype")
    String prototype() { return getResult(prototypeDifferentName); }

    private String getResult(Object obj){
        return obj + "\n" + obj + "\n" + obj;
    }
}
