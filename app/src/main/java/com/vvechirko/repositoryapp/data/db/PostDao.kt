package com.vvechirko.repositoryapp.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.vvechirko.repositoryapp.data.entity.PostEntity
import io.reactivex.Maybe

@Dao
abstract class PostDao {

    @Query("SELECT * FROM posts")
    abstract fun getAll(): Maybe<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<PostEntity>)

    @Delete
    abstract fun deleteAll(users: List<PostEntity>)

    @Query("DELETE FROM posts")
    abstract fun deleteAll()

    @RawQuery
    abstract fun rawQuery(query: SupportSQLiteQuery): Maybe<List<PostEntity>>
}