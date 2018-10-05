package com.vvechirko.repositoryapp.data.db

import androidx.sqlite.db.SimpleSQLiteQuery
import com.vvechirko.repositoryapp.App
import com.vvechirko.repositoryapp.data.AppDatabase
import com.vvechirko.repositoryapp.data.DataSource
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import com.vvechirko.repositoryapp.data.entity.PostEntity
import com.vvechirko.repositoryapp.data.entity.UserEntity

object DbData {

    val db: AppDatabase by lazy { AppDatabase.getInstance(App.appContext()) }

    inline fun <reified T : Any> of(): DataSource<T> {
        return when (T::class) {
            UserEntity::class -> UserDbData(db.getUserDao()) as DataSource<T>
            PostEntity::class -> PostDbData(db.getPostDao()) as DataSource<T>
            CommentEntity::class -> CommentDbData(db.getCommentDao()) as DataSource<T>
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    fun clearDb() {
        db.clearAllTables()
    }

    // util method for converting PARAMS MAP to sql QUERY with WHERE keyword
    fun sqlWhere(table: String, params: Map<String, String>): SimpleSQLiteQuery {
        var query = "SELECT * FROM $table"
        params.keys.forEachIndexed { i, s ->
            query += if (i == 0) " WHERE" else " AND"
            query += " $s = ?"
        }

        val args = params.values.toTypedArray()
        return SimpleSQLiteQuery(query, args)
    }
}