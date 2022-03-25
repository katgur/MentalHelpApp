package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "answers")
data class Answer(
    //todo map answers and fields
    val answers: List<String>,
    val depressed: Int,
    val anxious: Int,
    val stress: Int,
    val problem: String,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)