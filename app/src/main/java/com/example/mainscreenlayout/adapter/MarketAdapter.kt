package com.example.mainscreenlayout.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mainscreenlayout.R

class MarketAdapter(private val items : HashSet<Int> = hashSetOf()) : RecyclerView.Adapter<MarketAdapter.MarketItemHolder>() {

    var onItemClick: ((Pair<Int, Int>) -> Unit)? = null
    var onDeleteItemButtonClick: ((Pair<Int, Int>) -> Unit)? = null

    private var selected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketAdapter.MarketItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_market, parent, false)
        return MarketItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarketAdapter.MarketItemHolder, position: Int) {
        val id = ArrayList(items)[position]
        holder.bind(id, position)
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items : HashSet<Int>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setSelected(position : Int) {
        selected = position
        notifyDataSetChanged()
    }

    inner class MarketItemHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.recycler_market_iv)
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.recycler_market_delete_btn)

        fun bind(img: Int, position: Int) {
            if (selected == position) {
                image.setColorFilter(Color.MAGENTA)
            }
            else {
                image.setColorFilter(Color.TRANSPARENT)
            }
            image.setImageDrawable(AppCompatResources.getDrawable(itemView.context, img))
            image.setOnClickListener {
                onItemClick?.invoke(Pair(img, position))
            }
            deleteBtn.setOnClickListener {
                onDeleteItemButtonClick?.invoke(Pair(img, position))
            }
        }
    }
}