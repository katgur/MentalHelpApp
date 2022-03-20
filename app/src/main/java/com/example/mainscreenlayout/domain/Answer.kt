package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//todo make type converter
@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "answers")
    val answers: List<String>
)