package com.example.mainscreenlayout.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.HistoryItem
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class HistoryAdapter(private val history : ArrayList<HistoryItem> = arrayListOf()) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

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

    fun setItems(items: ArrayList<HistoryItem>) {
        history.clear()
        history.addAll(items.asReversed())
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.history_image)
        private val description = itemView.findViewById<TextView>(R.id.history_description)
        private val date = itemView.findViewById<TextView>(R.id.history_date)

        fun bind(item: HistoryItem) {
            if (item.answer_id != null) {
                image.setImageResource(R.drawable.question_icon)
            } else if (item.record_id != null) {
                image.setImageResource(R.drawable.record_icon)
            }
            description.text = item.description
            date.text = LocalDateTime.ofEpochSecond(item.date, 0, ZoneOffset.ofHours(3))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm"))
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}