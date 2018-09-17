package com.vvechirko.repositoryapp.data.entity

data class AddressModel(
        var street: String? = null,
        var suite: String? = null,
        var city: String? = null,
        var zipcode: String? = null
)