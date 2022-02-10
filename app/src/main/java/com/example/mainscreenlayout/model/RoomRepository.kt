package com.example.mainscreenlayout.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class RoomRepository {

    fun getRecommended(): LiveData<List<String>> {
        val res = MediatorLiveData<List<String>>()
        res.postValue(listOf("mock1", "mock2", "mock3", "mock4", "mock5"))
        return res
    }
}