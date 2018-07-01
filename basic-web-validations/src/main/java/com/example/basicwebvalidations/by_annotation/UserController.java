package com.example.basicwebvalidations.by_annotation;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController("byAnnotationController")
@RequestMapping("/users")
public class UserController {

    @PostMapping("/annotation")
    public List<ObjectError> byAnnotation(@Valid @RequestBody User user, BeanPropertyBindingResult errors){

        return errors.getAllErrors();
    }

}
