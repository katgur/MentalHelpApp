package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "history",
foreignKeys = [
    ForeignKey(entity = Record::class,
        parentColumns = ["id"],
        childColumns = ["record_id"],
        onDelete = CASCADE),
ForeignKey(entity = Question::class,
parentColumns = ["id"],
childColumns = ["answer_id"],
    onDelete = CASCADE)])
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    @ColumnInfo(name = "ref_id")
    val record_id : Long,
    @ColumnInfo(name = "answer_id")
    val answer_id : Long,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "date")
    val date : String
)