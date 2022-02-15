package com.example.mainscreenlayout.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R

open class HomeScreenItemAdapter(var list: List<String> = mutableListOf()) : RecyclerView.Adapter<HomeScreenItemAdapter.HomeScreenItemItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemItemHolder {
        return HomeScreenItemItemHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: HomeScreenItemItemHolder, position: Int) {
        return
    }

    override fun getItemCount(): Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<String>) {
        list += items
        notifyDataSetChanged()
    }

    open class HomeScreenItemItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(content: String, onItemClick: ((String) -> Unit)?) {
            return
        }
    }
}