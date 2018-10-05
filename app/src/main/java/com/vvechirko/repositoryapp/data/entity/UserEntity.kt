package com.vvechirko.repositoryapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey
        var id: String,
        var name: String,
        var username: String,
        var phone: String? = null,
        var website: String? = null
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is UserEntity) other.id == id else false
    }
}