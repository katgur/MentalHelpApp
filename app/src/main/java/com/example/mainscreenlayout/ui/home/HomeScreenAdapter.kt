package com.example.mainscreenlayout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.Message
import com.example.mainscreenlayout.ui.chat.ChatAdapter

class HomeScreenAdapter(private val headings: List<String>, private val childAdapter: ExerciseAdapter) : RecyclerView.Adapter<HomeScreenAdapter.HomeScreenItemHolder>() {

    //private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_item, parent, false)
        return HomeScreenItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeScreenItemHolder, position: Int) {
        val heading: String = headings[position]
        holder.bind(heading, childAdapter)
    }

    override fun getItemCount(): Int = headings.size

    class HomeScreenItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.home_item_text)
        private val recycler: RecyclerView = itemView.findViewById(R.id.recycler_exercise)

        fun bind(heading: String, exerciseAdapter: ExerciseAdapter) {
            text.text = heading
            recycler.layoutManager = LinearLayoutManager(recycler.context, LinearLayoutManager.HORIZONTAL, false)
            recycler.adapter = exerciseAdapter
        }
    }
}