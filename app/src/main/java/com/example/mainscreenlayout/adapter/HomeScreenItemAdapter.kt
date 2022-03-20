package com.example.mainscreenlayout.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.domain.MarkableItem

open class HomeScreenItemAdapter(var list: List<MarkableItem> = mutableListOf()) : RecyclerView.Adapter<HomeScreenItemAdapter.HomeScreenItemItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenItemItemHolder {
        return HomeScreenItemItemHolder(View(parent.context))
    }

    override fun onBindViewHolder(holder: HomeScreenItemItemHolder, position: Int) {
        return
    }

    override fun getItemCount(): Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<MarkableItem>) {
        list += items
        notifyDataSetChanged()
    }

    open class HomeScreenItemItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(content: MarkableItem, onItemClick: ((MarkableItem) -> Unit)?) {
            return
        }
    }
}