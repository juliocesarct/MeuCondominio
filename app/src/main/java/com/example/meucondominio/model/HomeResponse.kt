package com.example.meucondominio.model

data class HomeResponse(val content: List<HomeAdvices>)

data class HomeAdvices(
    val number : String,
    val name : String,
    val description: String,
    val imageURL : String
)