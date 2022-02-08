package com.example.mainscreenlayout.model

import androidx.lifecycle.MutableLiveData
import java.util.*

class RandomMessageRepository(override var messages: MutableLiveData<Message>) : IMessageRepository {
    private val random : Random = Random();

    override fun addMessage(path: String) {
        val name = if (random.nextInt() % 2 == 0) "me" else "other"
        messages.postValue(Message(randomContent(), User(name), random.nextLong()))
    }

    private fun randomContent():String {
        val length = random.nextInt(30)
        var res = ""
        for (i in 0..length step 1) {
            res += Char(random.nextInt(100))
        }
        return res
    }
}