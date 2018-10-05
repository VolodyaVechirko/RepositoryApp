package com.vvechirko.repositoryapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
        @PrimaryKey
        var id: String,
        var postId: String,
        var name: String,
        var email: String,
        var body: String
) {
    fun toMap(): Map<String, String> {
        return mapOf(
                "postId" to postId,
                "name" to name,
                "email" to email,
                "body" to body
        )
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is CommentEntity) other.id == id else false
    }
}