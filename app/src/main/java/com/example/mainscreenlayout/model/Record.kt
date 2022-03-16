package com.example.mainscreenlayout.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.full.memberProperties

@Entity
class Record(private val columns : LinkedHashMap<String, String>) {

    @PrimaryKey(autoGenerate = true)
    val uid : Int = 0

    @ColumnInfo(name = "content")
    val content : String = ""

    private val keys = ArrayList<String>(columns.keys)
    private var position = 0

    fun add(column : String) : Boolean {
        if (position >= keys.size) {
            return false
        }
        columns[keys[position]] = column
        position++
        return true
    }
}