package com.vvechirko.repositoryapp.data.entity

import androidx.room.PrimaryKey

data class PostEntity(
        @PrimaryKey
        var id: String,
        var title: String,
        var body: String,
        var userId: String
) {
    fun toMap(): Map<String, String> {
        return mapOf(
                "title" to title,
                "body" to body,
                "userId" to userId
        )
    }
}