package com.example.mainscreenlayout.ui.chat

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.*

class ChatViewModel(private val exercise: Exercise, context: FragmentActivity?) : ViewModel() {

    var isBot: Boolean = true

    private val firestoreRepository: FirestoreRepository = FirestoreRepository(context)

    val messageRepository: IMessageRepository = MessageRepositoryImpl(MutableLiveData<Message>())
    val commandRepository: CommandRepository = CommandRepository()

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        if (command == "Эффективность") {
            //firestoreRepository.get(exercise.name + "/" + "efficiency")
        }
        else if (command == "Время освоения") {
            //firestoreRepository.get(exercise.name + "/" + "duration")
        }
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        // todo save into table
    }
}