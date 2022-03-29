package com.example.mainscreenlayout.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R

class CommandAdapter(private var commands: List<String>) : RecyclerView.Adapter<CommandAdapter.CommandHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandHolder {
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
    fun setItems(commands: List<String>) {
        this.commands = commands
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        commands = emptyList()
        notifyDataSetChanged()
    }

    inner class CommandHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val button: Button = itemView.findViewById(R.id.command_text)

        fun bind(command: String) {
            button.text = command
            button.setOnClickListener {
                onItemClick?.invoke(command)
            }
        }
    }
}