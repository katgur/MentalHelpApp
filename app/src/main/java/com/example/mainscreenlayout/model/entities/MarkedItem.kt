package com.example.mainscreenlayout.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mainscreenlayout.model.entities.Record
import java.util.*

@Entity(tableName = "marked",
    foreignKeys = [
        ForeignKey(entity = Record::class,
            parentColumns = ["id"],
            childColumns = ["record_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )])
data class MarkedItem(
    val record_id: String?,
    val exercise_id: String?,
    val content: String,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)