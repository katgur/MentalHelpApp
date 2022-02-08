package com.example.mainscreenlayout.model

interface IIterator {

    var current: Int

    fun hasNext(): Boolean

    fun getNext(): Pair<String, String>
}