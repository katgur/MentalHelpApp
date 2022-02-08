package com.example.mainscreenlayout.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.CommandAdapter
import com.example.mainscreenlayout.databinding.ChatFragmentBinding
import com.example.mainscreenlayout.utils.QueryUtils

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private var chatViewManager = LinearLayoutManager(context)
    private var commandChatViewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ChatFragmentBinding

    private val chatAdapter: ChatAdapter = ChatAdapter(listOf())
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

        binding.recyclerChat.layoutManager = chatViewManager
        binding.recyclerChat.adapter = chatAdapter
        viewModel.messageRepository.messages.observe(viewLifecycleOwner, {
            chatAdapter.addItem(it)
        })
        binding.buttonChatSend.setOnClickListener {
            viewModel.sendMessage(QueryUtils.collections[0] + "/" + QueryUtils.structure[1])
        }

        binding.recyclerChatCommand.layoutManager = commandChatViewManager
        binding.recyclerChatCommand.adapter = commandAdapter
        viewModel.commandRepository.commands.observe(viewLifecycleOwner, {
            commandAdapter.addItem(it)
        })
        viewModel.addCommands("")
    }
}