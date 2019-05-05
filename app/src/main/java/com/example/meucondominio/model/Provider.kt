package com.example.meucondominio.model

data class ProviderResponse(val content: List<Provider>)

class Provider {
    var idProvider: String? = null
    var userAddedProvider: String? = null
    var providerPhone: String? = null
    var providerTitle: String? = null
    var providerDescription: String? = null
    //var imageURL : String? = null
}