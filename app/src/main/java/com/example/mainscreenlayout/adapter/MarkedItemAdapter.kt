package com.example.mainscreenlayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.domain.MarkedItem

class MarkedItemAdapter(private val  favourites : List<MarkedItem>) : RecyclerView.Adapter<MarkedItemAdapter.MarkedItemViewHolder>()  {

    var onItemClick: ((MarkedItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkedItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history, parent, false)
        return MarkedItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarkedItemViewHolder, position: Int) {
        val item = favourites[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = favourites.size

    inner class MarkedItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.history_image)
        private val description = itemView.findViewById<TextView>(R.id.history_description)
        private val date = itemView.findViewById<TextView>(R.id.history_date)

        fun bind(item : MarkedItem) {
            image.setImageResource(R.drawable.ic_launcher_background)
            description.text = item.content
            date.text = ""
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}