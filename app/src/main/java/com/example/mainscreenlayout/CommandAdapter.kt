package com.example.mainscreenlayout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommandAdapter(private var commands: List<String>) : RecyclerView.Adapter<CommandAdapter.CommandHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommandHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_command, parent, false)
        return CommandHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommandHolder, position: Int) {
        val command: String = commands[position]
        holder.bind(command)
    }

    override fun getItemCount(): Int = commands.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(command: String) {
        commands += command
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        commands = emptyList()
        notifyDataSetChanged()
    }

    class CommandHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.command_text)

        fun bind(command: String) {
            text.text = command
        }
    }
}