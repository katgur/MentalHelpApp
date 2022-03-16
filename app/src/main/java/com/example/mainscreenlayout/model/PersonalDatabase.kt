package com.example.mainscreenlayout.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[Record::class], version=1)
abstract class PersonalDatabase : RoomDatabase() {

    abstract fun dao() : PersonalDataDao

    companion object {
        @Volatile
        private var INSTANCE: PersonalDatabase? = null
        fun getInstance(context: Context): PersonalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonalDatabase::class.java,
                        "personal_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}