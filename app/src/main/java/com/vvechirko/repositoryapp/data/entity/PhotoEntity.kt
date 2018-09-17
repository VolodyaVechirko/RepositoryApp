package com.vvechirko.repositoryapp.data.entity

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PhotoEntity(
        @PrimaryKey
        var id: String,
        var albumId: String,
        var title: String,
        var url: String,
        @SerializedName("thumbnailUrl")
        var thumb: String
)