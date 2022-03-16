package com.example.mainscreenlayout.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PersonalDataDao {

    @Query("SELECT * FROM record WHERE uid = :id")
    fun getRecord(id : Int) : Record

}