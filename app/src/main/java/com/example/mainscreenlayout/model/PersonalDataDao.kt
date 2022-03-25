package com.example.mainscreenlayout.model

import androidx.room.*
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.domain.Record

@Dao
interface PersonalDataDao {

    @Query("SELECT * FROM record WHERE id = :id")
    fun getRecord(id : String) : Record

    @Insert
    fun addRecord(record : Record)

    @Query("SELECT * FROM history")
    fun getAllHistory() : List<HistoryItem>

    @Insert
    fun addHistoryItem(item : HistoryItem)

    @Insert
    fun addFavourite(markedItem: MarkedItem)

    @Query("SELECT * FROM marked")
    fun getAllFavourites() : List<MarkedItem>

    @Update
    fun updateRecord(record : Record)

    @Delete
    fun deleteRecord(record : Record)

    @Insert
    fun addAnswer(answer : Answer)

    @Query("SELECT * FROM answers WHERE id = :id")
    fun getAnswer(id: String) : Answer

    @Update
    fun updateAnswer(answer : Answer)

    @Delete
    fun deleteAnswer(answer : Answer)

    @Query("SELECT * FROM record")
    fun getAllRecords() : List<Record>

    @Query("SELECT * FROM answers")
    fun getAllAnswers() : List<Answer>

    @Insert
    fun addRecords(records : List<Record>)

    @Insert
    fun addHistory(history : List<HistoryItem>)

    @Insert
    fun addAnswers(answers : List<Answer>)

    @Insert
    fun addFavourites(favourites : List<MarkedItem>)

    @Query("SELECT * FROM " +
            "history JOIN answers ON history.answer_id = answers.id " +
            "WHERE history.date >= :start AND history.date <= :end")
    fun getAnswersBetween(start : Long, end : Long) : Map<HistoryItem, List<Answer>>

    @Query("SELECT * FROM " +
            "history JOIN answers ON history.answer_id = answers.id " +
            "ORDER BY history.date DESC " +
            "LIMIT 1")
    fun getLastAnswer() : Map<HistoryItem, List<Answer>>
}