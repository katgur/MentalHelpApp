package com.example.mainscreenlayout.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Record(@PrimaryKey(autoGenerate = true)
             val id : Long,
             @ColumnInfo(name = "type")
             val type : String,
             @ColumnInfo(name = "columns")
             val columns : LinkedHashMap<String, String>) {

    @Ignore
    private val keys = ArrayList<String>(columns.keys)
    @Ignore
    private var position = 0

    @Ignore
    fun add(column : String) : Boolean {
        if (position >= keys.size) {
            return false
        }
        columns[keys[position]] = column
        position++
        return true
    }
}