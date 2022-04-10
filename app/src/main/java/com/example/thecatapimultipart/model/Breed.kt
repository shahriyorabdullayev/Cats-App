package com.example.thecatapimultipart.model

data class Breed(
    val id: String,
    val name: String,
    val temprament: String?,
    val description: String?,
    val image: Cat,
    var expand: Boolean = false
)
