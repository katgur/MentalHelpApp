package com.example.mainscreenlayout.utils

object QueryUtils {
    val delimeter: Char = '/'
    val collections = listOf("authomatic_thoughts", "thought_journal")
    val structure = listOf("intro", "efficiency", "duration", "steps")
    //todo
    val nameToId = mapOf(Pair("Журнал мыслей", "thought_journal"),
    Pair("Горячие мысли", "hot_thought"),
    Pair("Визулизация", "visualization"))
}
