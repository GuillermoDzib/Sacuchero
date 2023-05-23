package com.bedu.sacuchero.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bedu.sacuchero.model.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract  class UsersDataBase: RoomDatabase() {
    abstract fun wordDao(): UsersDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UsersDataBase? = null

        fun getDatabase(context: Context): UsersDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, UsersDataBase::class.java,
                    "users_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}