package com.vvechirko.repositoryapp.data.api

import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import com.vvechirko.repositoryapp.data.entity.PostEntity
import com.vvechirko.repositoryapp.data.entity.UserEntity
import kotlin.reflect.KClass

object ApiData {

    fun <Entity : Any> of(clazz: KClass<*>): DataSource<Entity> {
        return when (clazz) {
            UserEntity::class -> UserApiData()
            PostEntity::class -> PostApiData()
            CommentEntity::class -> CommentApiData()
            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<Entity>
    }
}