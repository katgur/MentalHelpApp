package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.MarkableItem

class RoundedRectangleItemAdapter : HomeScreenItemAdapter() {

    var onItemClick: ((MarkableItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundedRectangleItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_rounded_rectangle_item, parent, false)
        return RoundedRectangleItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeScreenItemItemHolder, position: Int) {
        val exercise = list[position]
        holder.bind(exercise, onItemClick)
    }

    override fun getItemCount(): Int = list.size


    class RoundedRectangleItemHolder(itemView: View) : HomeScreenItemItemHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.rounded_rect_text)

        override fun bind(content: MarkableItem, onItemClick: ((MarkableItem) -> Unit)?) {
            text.text = content.name
            itemView.setOnClickListener {
                onItemClick?.invoke(content)
            }
        }
    }
}