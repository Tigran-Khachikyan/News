package com.example.news.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb

@androidx.room.Database(
    entities = [ModelDb::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: Database? = null

        operator fun invoke(context: Context): Database {
            return instance ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "GUARDIAN_DB"
                ).build()
                this.instance = instance
                return instance
            }
        }
    }
}