package com.example.mainscreenlayout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mainscreenlayout.R

class RoundItemAdapter : HomeScreenItemAdapter() {

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
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = list.size

    class RoundListItemHolder(itemView: View) : HomeScreenItemItemHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.round_text)

        override fun bind(content: String) {
            text.text = content
        }
    }
}