package com.example.mainscreenlayout.ui.chat

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.*

class ChatViewModel(private val exercise: Exercise, val context: FragmentActivity?) : ViewModel() {

    var isBot: Boolean = true

    private val firestoreRepository = FirestoreRepository(this, context)

    val messageRepository: IMessageRepository = MessageRepositoryImpl(MutableLiveData<Message>())
    val commandRepository: CommandRepository = CommandRepository()

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        if (command == "Эффективность") {
            firestoreRepository.get(exercise.name + "/" + "efficiency")
        }
        else if (command == "Время освоения") {
            firestoreRepository.get(exercise.name + "/" + "duration")
        }
        else {
            Toast.makeText(context, "error while processing command", Toast.LENGTH_LONG).show()
        }
    }

    fun onResponse(answer: ArrayList<String>) {
        messageRepository.addMessage(Message(answer[0], "bot", 0))
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        // todo save into table
    }
}