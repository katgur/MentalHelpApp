package com.example.mainscreenlayout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mainscreenlayout.R

class RoundedRectangleItemAdapter : HomeScreenItemAdapter() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundedRectangleItemAdapter.RoundedRectangleItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_rounded_rectangle_item, parent, false)
        return RoundedRectangleItemAdapter.RoundedRectangleItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeScreenItemItemHolder, position: Int) {
        val exercise = list[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = list.size


    class RoundedRectangleItemHolder(itemView: View) : HomeScreenItemAdapter.HomeScreenItemItemHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.rounded_rect_text)

        override fun bind(content: String) {
            text.text = content
        }
    }
}