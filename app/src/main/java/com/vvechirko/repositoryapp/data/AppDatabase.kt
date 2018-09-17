package com.vvechirko.repositoryapp.data

import android.content.Context
import androidx.room.*
import com.vvechirko.repositoryapp.data.db.UserDao
import com.vvechirko.repositoryapp.data.entity.CommentEntity
import com.vvechirko.repositoryapp.data.entity.PostEntity
import com.vvechirko.repositoryapp.data.entity.UserEntity
import java.util.*

@Database(
        entities = [
            UserEntity::class,
            PostEntity::class,
            CommentEntity::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        private const val DB_NAME = "testapp1.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }
}

class DateConverter {

    @TypeConverter
    fun toDate(value: Long?) = value?.let { Date(value) }

    @TypeConverter
    fun toLong(value: Date?) = value?.time
}