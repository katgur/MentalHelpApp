package com.example.mainscreenlayout.model

import android.app.Activity
import android.content.Context
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.model.entities.Record
import java.time.LocalDateTime
import java.time.ZoneOffset

class RoomRepository {

    fun getLastAnswer(context : Context) : Pair<LocalDateTime, Answer>? {
        val lastHistoryAnswer = PersonalDatabase.getInstance(context).dao().getLastAnswer()
        val keys = lastHistoryAnswer.keys.toList()
        val values = lastHistoryAnswer.values.toList()
        if (keys.isNotEmpty() && values.isNotEmpty()) {
            val lastDateTime = LocalDateTime.ofEpochSecond(keys[0].date,0, ZoneOffset.UTC)
            val lastAnswer = values[0][0]
            return Pair(lastDateTime, lastAnswer)
        }
        return null
    }

    fun addHistoryItem(context: Context, historyItem: HistoryItem) {
        PersonalDatabase.getInstance(context).dao().addHistoryItem(historyItem)
    }

    fun addAnswer(activity: Activity, answer: Answer) {
        PersonalDatabase.getInstance(activity).dao().addAnswer(answer)
    }

    fun updateAnswer(activity: Activity, answer: Answer) {
        PersonalDatabase.getInstance(activity).dao().updateAnswer(answer)
    }

    fun getAnswersIn(context: Context, start: Long, end: Long): Map<HistoryItem, List<Answer>> {
        return PersonalDatabase.getInstance(context).dao().getAnswersBetween(start, end)
    }

    fun addRecord(context: Context, record: Record) {
        PersonalDatabase.getInstance(context).dao().addRecord(record)
    }
}