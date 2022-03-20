package com.example.mainscreenlayout.model

import androidx.room.*
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.domain.Record

@Dao
interface PersonalDataDao {

    @Query("SELECT * FROM record WHERE id = :id")
    fun getRecord(id : Long) : Record

    @Insert
    fun addRecord(record : Record) : Long

    @Query("SELECT * FROM history")
    fun getAllHistory() : List<HistoryItem>

    @Insert
    fun addHistoryItem(item : HistoryItem)

    @Insert
    fun addFavourite(markedItem: MarkedItem)

    @Query("SELECT * FROM marked")
    fun getFavourites() : List<MarkedItem>

    @Update
    fun updateRecord(record : Record)

    @Delete
    fun deleteRecord(record : Record)

    @Insert
    fun addAnswer(answer : Answer) : Long
}