package com.example.mainscreenlayout.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mainscreenlayout.domain.MarkableItem

class RoomRepository {

    fun getRecommended(): LiveData<List<MarkableItem>> {
        return MutableLiveData()
    }
}