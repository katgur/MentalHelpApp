package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.MarkableItem

class RoundItemAdapter(private val list : ArrayList<MarkableItem> = arrayListOf()) : RecyclerView.Adapter<RoundItemAdapter.RoundListItemHolder>() {

    var onItemClick: ((MarkableItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoundListItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_round_item, parent, false)
        return RoundListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoundListItemHolder, position: Int) {
        val exercise = list[position]
        holder.bind(exercise, onItemClick)
    }

    override fun getItemCount(): Int = list.size

    fun addItems(items: List<MarkableItem>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    class RoundListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.round_text)

        fun bind(content: MarkableItem, onItemClick: ((MarkableItem) -> Unit)?) {
            text.text = content.name
            text.setOnClickListener {
                onItemClick?.invoke(content)
            }
        }
    }
}