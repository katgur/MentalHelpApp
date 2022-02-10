package com.example.mainscreenlayout.ui.chat

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.Message

const val VIEW_TYPE_MESSAGE_SENT = 1
const val VIEW_TYPE_MESSAGE_RECEIVED = 2
class ChatAdapter(private var messages: List<Message> = mutableListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_message1, parent, false)
            return SentMessageHolder(itemView)
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_message2, parent, false)
            return ReceivedMessageHolder(itemView)
        }
        return SentMessageHolder(View(parent.context))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Message = messages[position]

        if (holder.itemViewType == VIEW_TYPE_MESSAGE_SENT) {
            (holder as SentMessageHolder).bind(message)
        } else if (holder.itemViewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        val message: Message = messages[position]

        return if (message.sentBy == "me") {
            VIEW_TYPE_MESSAGE_SENT
        } else {
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(message: Message) {
        messages += message
        notifyDataSetChanged()
    }

    class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @androidx.annotation.RequiresApi(Build.VERSION_CODES.O)
        fun bind(message: Message) {
            val contentTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_message_other)
            val dateTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_date_other)
            val timeTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_timestamp_other)

            contentTextView.text = message.content
            dateTextView.text = java.time.LocalDate.now().toString()
            timeTextView.text = java.time.LocalTime.now().toString()
        }
    }

    class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @androidx.annotation.RequiresApi(Build.VERSION_CODES.O)
        fun bind(message: Message) {
            val contentTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_message_me)
            val dateTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_date_me)
            val timeTextView: android.widget.TextView = itemView.findViewById(R.id.text_gchat_timestamp_me)

            contentTextView.text = message.content
            dateTextView.text = java.time.LocalDate.now().toString()
            timeTextView.text = java.time.LocalTime.now().toString()
        }
    }
}