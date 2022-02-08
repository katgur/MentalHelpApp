package com.example.mainscreenlayout.model

import androidx.lifecycle.MutableLiveData

class Chat(private val exercise: Exercise) {

    private var turn: Turn = Turn.BOT

    val messageRepository: IMessageRepository = FirestoreDatabaseMessageRepository(context, MutableLiveData<Message>())
    val commandRepository: CommandRepository = CommandRepository()

    fun start() {

    }

    fun switchTurn() {
        turn = if (turn == Turn.BOT) {
            Turn.USER
        } else {
            Turn.BOT
        }
    }
}