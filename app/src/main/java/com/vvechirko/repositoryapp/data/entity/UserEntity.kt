package com.vvechirko.repositoryapp.data.entity

import androidx.room.PrimaryKey

data class UserEntity(
        @PrimaryKey
        var id: String,
        var name: String,
        var username: String,
        var address: AddressModel? = null,
        var phone: String? = null,
        var website: String? = null,
        var company: CompanyModel? = null
)