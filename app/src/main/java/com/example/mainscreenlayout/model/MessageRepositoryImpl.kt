package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageRepositoryImpl(override var messages: MutableLiveData<Message>) : IMessageRepository {

    override fun addMessage(message: Message) {
        messages.postValue(message)
    }
}