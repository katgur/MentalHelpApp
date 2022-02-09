package com.example.mainscreenlayout.model

import androidx.fragment.app.FragmentActivity
import com.example.mainscreenlayout.ui.chat.ChatViewModel

class FirestoreRepository(private val viewModel: ChatViewModel, private val context: FragmentActivity?) : IObserver {

    private val dao = FirestoreDatabase(context)

    init {
        dao.subscribe(this)
    }

    fun get(query: String) {
        dao.get(query)
    }

    override fun onResponse(response: ArrayList<String>) {
        viewModel.onResponse(response)
    }

    override fun onFailure(response: String) {
        TODO("Not yet implemented")
    }
}