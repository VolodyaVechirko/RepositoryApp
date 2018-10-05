package com.vvechirko.repositoryapp.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
        @PrimaryKey
        var id: String,
        var title: String,
        var body: String,
        var userId: String
) {

    @Ignore
    var comments: List<CommentEntity> = listOf()

    fun toMap(): Map<String, String> {
        return mapOf(
                "title" to title,
                "body" to body,
                "userId" to userId
        )
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is PostEntity) other.id == id else false
    }
}