package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.HistoryItem

class HistoryAdapter(private val history : List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var onItemClick: ((HistoryItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = history[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = history.size

    inner class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.history_image)
        private val description = itemView.findViewById<TextView>(R.id.history_description)
        private val date = itemView.findViewById<TextView>(R.id.history_date)

        fun bind(item: HistoryItem) {
            image.setImageResource(R.drawable.ic_launcher_background)
            description.text = item.description
            date.text = item.date
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}