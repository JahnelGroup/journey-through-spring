package com.example.basicvalidations.by_annotation

import javax.validation.constraints.NotNull

data class User(
    var username: String?,

    @field:NotNull
    var firstName: String?
)