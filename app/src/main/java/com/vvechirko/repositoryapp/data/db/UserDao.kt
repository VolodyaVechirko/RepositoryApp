package com.vvechirko.repositoryapp.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.vvechirko.repositoryapp.data.entity.UserEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class UserDao {

    @Query("SELECT * FROM users")
    abstract fun getAll(): Maybe<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<UserEntity>)

    @Delete
    abstract fun deleteAll(users: List<UserEntity>)

    @Query("DELETE FROM users")
    abstract fun deleteAll()

    @RawQuery
    abstract fun rawQuery(query: SupportSQLiteQuery): Maybe<List<UserEntity>>
}