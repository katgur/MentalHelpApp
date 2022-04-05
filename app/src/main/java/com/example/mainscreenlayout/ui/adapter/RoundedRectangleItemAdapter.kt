package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.MarkableItem

class RoundedRectangleItemAdapter(private val list: ArrayList<MarkableItem> = arrayListOf()) : RecyclerView.Adapter<RoundedRectangleItemAdapter.RoundedRectangleItemHolder>() {

    var onItemClick: ((MarkableItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundedRectangleItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_rounded_rectangle_item, parent, false)
        return RoundedRectangleItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoundedRectangleItemHolder, position: Int) {
        val exercise = list[position]
        holder.bind(exercise, onItemClick)
    }

    override fun getItemCount(): Int = list.size

    fun addItems(items: List<MarkableItem>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class RoundedRectangleItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.rounded_rect_text)

        fun bind(content: MarkableItem, onItemClick: ((MarkableItem) -> Unit)?) {
            text.text = content.name
            itemView.setOnClickListener {
                onItemClick?.invoke(content)
            }
        }
    }
}