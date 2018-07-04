package com.example.basicwebvalidations.by_annotation;

import com.example.basicwebvalidations.by_annotation.validator.Username;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {

    @NotNull
    @Username
    private String username;

    @NotNull
    private String firstName;
//
//    @NotNull
//    private String lastName;
//
//    @NotNull
//    private String state;
//
//    @NotNull
//    private Integer age;

}
