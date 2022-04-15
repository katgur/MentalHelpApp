package com.example.mainscreenlayout.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.mainscreenlayout.model.entities.Record
import java.util.*

@Entity(tableName = "history",
foreignKeys = [
    ForeignKey(entity = Record::class,
        parentColumns = ["id"],
        childColumns = ["record_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE),
ForeignKey(entity = Answer::class,
parentColumns = ["id"],
childColumns = ["answer_id"],
    onUpdate = CASCADE,
    onDelete = CASCADE)])
data class HistoryItem(
    val record_id : String?,
    val answer_id : String?,
    val description : String?,
    val date : Long,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)