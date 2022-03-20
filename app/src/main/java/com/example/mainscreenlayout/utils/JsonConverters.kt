package com.example.mainscreenlayout.utils

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class JsonConverters {

    @TypeConverter
    fun fromJson(value : String) : LinkedHashMap<String, String> {
        return Gson().fromJson(value, object : TypeToken<LinkedHashMap<String, String>>() {}.type)
    }

    @TypeConverter
    fun linkedHashMapToJson(value : LinkedHashMap<String, String>) : String {
        return Gson().toJson(value)
    }
}