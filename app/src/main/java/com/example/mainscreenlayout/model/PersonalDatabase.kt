package com.example.mainscreenlayout.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mainscreenlayout.model.Answer
import com.example.mainscreenlayout.model.HistoryItem
import com.example.mainscreenlayout.model.MarkedItem
import com.example.mainscreenlayout.utils.JsonConverters
import com.example.mainscreenlayout.model.entities.Record

@Database(entities=[Record::class, HistoryItem::class, MarkedItem::class, Answer::class], version=1)
@TypeConverters(JsonConverters::class)
abstract class PersonalDatabase : RoomDatabase() {

    abstract fun dao() : PersonalDataDao

    companion object {
        private var INSTANCE: PersonalDatabase? = null

        fun getInstance(context : Context): PersonalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        PersonalDatabase::class.java,
                        "personal_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}