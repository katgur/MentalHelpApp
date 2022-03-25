package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "marked")
data class MarkedItem(
    val item_id : String,
    val content : String,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)