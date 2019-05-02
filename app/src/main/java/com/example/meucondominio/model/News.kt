package com.example.meucondominio.model

class News {
    var user: String?
    var title: String?
    var description: String?
    //var imageURL : String? = null

    constructor(user: String? = null, title: String? = null, description: String? = null) {
        this.user = user
        this.title = title
        this.description = description
    }
}