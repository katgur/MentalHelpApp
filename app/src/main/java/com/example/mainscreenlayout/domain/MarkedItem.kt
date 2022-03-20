package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marked")
data class MarkedItem(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    @ColumnInfo(name = "item_id")
    val item_id : String,
    @ColumnInfo(name = "content")
    val content : String
)