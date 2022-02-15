package com.example.mainscreenlayout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mainscreenlayout.R

class RoundItemAdapter : HomeScreenItemAdapter() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundListItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_round_item, parent, false)
        return RoundListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeScreenItemItemHolder, position: Int) {
        val exercise = list[position]
        holder.bind(exercise, onItemClick)
    }

    override fun getItemCount(): Int = list.size

    class RoundListItemHolder(itemView: View) : HomeScreenItemItemHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.round_text)

        override fun bind(content: String, onItemClick: ((String) -> Unit)?) {
            text.text = content
            text.setOnClickListener {
                onItemClick?.invoke(content)
            }
        }
    }
}