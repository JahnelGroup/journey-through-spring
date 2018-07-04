package com.example.basicwebvalidations.by_interface;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("byInterfaceController")
@RequestMapping("/users")
public class UserController {

    UserValidator userValidator = new UserValidator();

    @PostMapping("/interface")
    public List<ObjectError> byInterface(@RequestBody User user){
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors);

        return errors.getAllErrors();
    }

}
