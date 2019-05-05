package com.example.meucondominio.model

data class NewsResponse(val content: List<News>)

class News {
    var idNews : String? = null
    var user: String? = null
    var title: String? = null
    var description: String? = null
}