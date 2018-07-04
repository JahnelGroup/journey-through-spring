package com.example.basicwebvalidations.by_interface;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        /**
         * Null Checks : The only field not required is state
          */
        rejectIfEmptyOrWhitespace(errors, "username", "firstName", "lastName", "age");


        /**
         * Username
         */
        if( user.getUsername() != null ){
            // must be between 4-20 characters
            int len = user.getUsername().length();
            if( len < 4 || len > 20 ){
                errors.rejectValue("username", "size");
            }

            // consist of only numbers and letters
            if( !user.getUsername().matches("^[A-Za-z0-9]+$") ){
                errors.rejectValue("username", "format");
            }
        }
    }

    private void rejectIfEmptyOrWhitespace(Errors errors, String...fields){
        for(String field : fields){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "empty");
        }
    }
}
