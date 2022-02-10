package com.example.mainscreenlayout.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class RoomRepository {

    fun getRecommended(): LiveData<List<String>> {
        return MediatorLiveData()
    }
}