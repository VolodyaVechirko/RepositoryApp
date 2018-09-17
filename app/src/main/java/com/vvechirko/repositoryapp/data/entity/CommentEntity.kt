package com.vvechirko.repositoryapp.data.entity

import androidx.room.PrimaryKey

data class CommentEntity(
        @PrimaryKey
        private var id: String,
        var postId: String,
        var name: String,
        var email: String,
        var body: String
)