package com.vvechirko.repositoryapp.data.entity

import androidx.room.PrimaryKey

data class AlbumEntity(
        @PrimaryKey
        var id: String,
        var userId: String,
        var title: String
)