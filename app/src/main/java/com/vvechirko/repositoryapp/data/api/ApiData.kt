package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.UserEntity

object ApiData {

    inline fun <reified T : Any> of(): DataSource<T> {
        return when (T::class) {
            UserEntity::class -> UserApiData() as DataSource<T>
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }
}