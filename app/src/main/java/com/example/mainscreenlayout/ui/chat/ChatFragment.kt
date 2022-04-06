package com.example.mainscreenlayout.ui.chat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainscreenlayout.adapter.CommandAdapter
import com.example.mainscreenlayout.adapter.ChatAdapter
import com.example.mainscreenlayout.databinding.ChatFragmentBinding
import com.example.mainscreenlayout.model.Message
import java.lang.IllegalStateException

class ChatFragment : Fragment() {

    companion object {
        fun newInstance(id : String) : ChatFragment {
            val instance = ChatFragment()
            val args = Bundle()
            args.putString("id", id)
            instance.arguments = args
            return instance
        }
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //todo catch illegal state exc in require argumanets
        viewModel = try {
            val k = requireArguments().getString("id", "default")
            ViewModelProvider(this, ChatViewModelFactory(k, requireActivity().application, viewLifecycleOwner))[ChatViewModel::class.java]
        } catch (e : IllegalStateException) {
            ViewModelProvider(this, ChatViewModelFactory("default", requireActivity().application, viewLifecycleOwner))[ChatViewModel::class.java]
        }
        
        // set chat recycler view
        chatViewManager.stackFromEnd = true
        binding.recyclerChat.layoutManager = chatViewManager
        binding.recyclerChat.adapter = chatAdapter
        viewModel.observeMessages(viewLifecycleOwner, {
            chatAdapter.addItem(it)
            binding.recyclerChat.adapter?.itemCount?.let { it1 ->
                binding.recyclerChat.scrollToPosition(
                    it1.minus(1))
            }
        })

        viewModel.observeEnterMode(viewLifecycleOwner, {
            binding.buttonChatSend.isEnabled = it
        })

        // set command recycler view
        binding.recyclerChatCommand.layoutManager = commandChatViewManager
        binding.recyclerChatCommand.adapter = commandAdapter
        viewModel.observeCommands(viewLifecycleOwner, {
            commandAdapter.setItems(it)
        })

        // set command button on click listener
        commandAdapter.onItemClick = {
            viewModel.processCommand(it)
        }

        // set send button on click listener
        binding.buttonChatSend.setOnClickListener {
            val message = binding.editGchatMessage.text.toString()
            viewModel.processMessage(Message(message, "me", 0))
            binding.editGchatMessage.setText("")
        }
    }
}