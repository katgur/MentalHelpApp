package com.example.mainscreenlayout.model

import android.content.Context
import com.example.mainscreenlayout.domain.Answer
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
}