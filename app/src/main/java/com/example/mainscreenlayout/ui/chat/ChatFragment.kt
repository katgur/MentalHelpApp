package com.example.mainscreenlayout.ui.chat

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.CommandAdapter
import com.example.mainscreenlayout.databinding.ChatFragmentBinding
import com.example.mainscreenlayout.model.Message
import com.example.mainscreenlayout.utils.QueryUtils

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private var chatViewManager = LinearLayoutManager(context)
    private var commandChatViewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ChatFragmentBinding

    private val chatAdapter: ChatAdapter = ChatAdapter(listOf(), )
    private val commandAdapter: CommandAdapter = CommandAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ChatViewModelFactory(activity)).get(ChatViewModel::class.java)

        // set chat recycler view
        binding.recyclerChat.layoutManager = chatViewManager
        binding.recyclerChat.adapter = chatAdapter
        viewModel.messageRepository.messages.observe(viewLifecycleOwner, {
            chatAdapter.addItem(it)
        })

        // set command recycler view
        binding.recyclerChatCommand.layoutManager = commandChatViewManager
        binding.recyclerChatCommand.adapter = commandAdapter
        viewModel.commandRepository.commands.observe(viewLifecycleOwner, {
            commandAdapter.addItem(it)
        })

        // set command button on click listener
        commandAdapter.onItemClick = {
            viewModel.processCommand(it)
        }

        // set send button on click listener
        binding.buttonChatSend.setOnClickListener {
            val message = binding.editGchatMessage.text.toString()
            viewModel.processMessage(Message(message, "me", 0))
        }
    }
}