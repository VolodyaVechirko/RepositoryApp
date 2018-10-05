package com.vvechirko.repositoryapp.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import io.reactivex.Maybe

@Dao
abstract class CommentDao {

    @Query("SELECT * FROM comments")
    abstract fun getAll(): Maybe<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<CommentEntity>)

    @Delete
    abstract fun deleteAll(users: List<CommentEntity>)

    @Query("DELETE FROM comments")
    abstract fun deleteAll()

    @RawQuery
    abstract fun rawQuery(query: SupportSQLiteQuery): Maybe<List<CommentEntity>>
}