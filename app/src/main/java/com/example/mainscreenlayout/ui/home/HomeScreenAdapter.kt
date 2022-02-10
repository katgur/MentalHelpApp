package com.example.mainscreenlayout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R

const val VIEW_TYPE_RECOMMENDED = 0
const val VIEW_TYPE_HELLO = 1
const val VIEW_TYPE_EXERCISE = 2
const val VIEW_TYPE_PACK = 3
class HomeScreenAdapter(private val headings: List<String>, private val childAdapters: List<HomeScreenItemAdapter>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_RECOMMENDED -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_round_home_item, parent, false)
                return RoundItemHolder(itemView)
            }
            VIEW_TYPE_HELLO -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_single_layout_home_item, parent, false)
                return SingleLayoutItemHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_rectangle_home_item, parent, false)
                return RoundedRectangleItemHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val heading: String = headings[position]

        when (holder.itemViewType) {
            VIEW_TYPE_RECOMMENDED -> (holder as RoundItemHolder).bind(heading, childAdapters[0])
            VIEW_TYPE_HELLO -> (holder as SingleLayoutItemHolder).bind(heading)
            VIEW_TYPE_EXERCISE -> (holder as RoundedRectangleItemHolder).bind(heading, childAdapters[1])
            VIEW_TYPE_PACK -> (holder as RoundedRectangleItemHolder).bind(heading, childAdapters[2])
        }
    }

    override fun getItemCount(): Int = headings.size

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> VIEW_TYPE_RECOMMENDED
            1 -> VIEW_TYPE_HELLO
            2 -> VIEW_TYPE_EXERCISE
            3 -> VIEW_TYPE_PACK
            else -> VIEW_TYPE_HELLO
        }

    class RoundedRectangleItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.home_item_text)
        private val recycler: RecyclerView = itemView.findViewById(R.id.rect_home_item_recycler)

        fun bind(heading: String, adapter: HomeScreenItemAdapter) {
            text.text = heading
            recycler.layoutManager = LinearLayoutManager(recycler.context, LinearLayoutManager.HORIZONTAL, false)
            recycler.adapter = adapter
        }
    }

    class RoundItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.home_item_text)
        private val recycler: RecyclerView = itemView.findViewById(R.id.round_home_item_recycler)

        fun bind(heading: String, adapter: HomeScreenItemAdapter) {
            text.text = heading
            recycler.layoutManager = LinearLayoutManager(recycler.context, LinearLayoutManager.HORIZONTAL, false)
            recycler.adapter = adapter
        }
    }

    class SingleLayoutItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text: TextView = itemView.findViewById(R.id.home_item_text)

        fun bind(heading: String) {
            text.text = heading
        }
    }
}