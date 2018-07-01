package com.example.basicvalidations

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class UserController {

    @PostMapping("/users/annotation")
    fun byAnnotation(@Valid @RequestBody user: com.example.basicvalidations.by_annotation.User){}

}