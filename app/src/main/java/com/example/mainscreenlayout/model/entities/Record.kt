package com.example.mainscreenlayout.model.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

@Entity
data class Record(
    val type : String,
    val columns : LinkedHashMap<String, String>,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()) {

    @Ignore
    private val keys = ArrayList<String>(columns.keys)
    @Ignore
    private var position = -1

    @Ignore
    fun add(column : String) : Boolean {
        if (position >= keys.size) {
            return false
        }
        columns[keys[position]] += column
        return true
    }

    fun setStep(step: Int): Boolean {
        if (step >= keys.size) {
            return false
        }
        position = step
        return true
    }

    fun next(): Boolean {
        if (position >= keys.size) {
            return false
        }
        position++
        return true
    }
}