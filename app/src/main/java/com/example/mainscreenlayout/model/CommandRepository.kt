package com.example.mainscreenlayout.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class CommandRepository {

    var commands: MutableLiveData<String> = MutableLiveData<String>("Эффективность")

    fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        commands.observe(owner, observer)
    }
}